package tw.north27.coachingapp.media

import android.content.Context
import android.net.Uri
import android.os.CancellationSignal
import android.provider.BaseColumns
import android.provider.MediaStore
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import tw.north27.coachingapp.chat.Media
import tw.north27.coachingapp.chat.MediaAlbum
import tw.north27.coachingapp.chat.MediaSetting
import tw.north27.coachingapp.chat.MimeType
import java.io.File

class MediaVideoModule(private val cxt: Context) : IMediaModule {

    val albumMap = mutableMapOf<String, MediaAlbum>()

    val isAlbumEmpty: Boolean = albumMap.isEmpty()

    val albumListRelay: PublishRelay<List<MediaAlbum>> = PublishRelay.create()

    override fun fetchMedia(setting: MediaSetting): Completable = Completable.fromAction {
        fetchMediaSync(setting)
    }

    override fun getMediaAlbum(setting: MediaSetting): PublishRelay<List<MediaAlbum>> {
        if (isAlbumEmpty)
            fetchMedia(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay
    }

    override fun getAlbumFromName(albumName: String, setting: MediaSetting): Observable<MediaAlbum?> {
        if (isAlbumEmpty)
            fetchMedia(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay.map { it.find { it.albumName == albumName } }
    }

    override fun getAlbumFromNameSync(albumName: String, setting: MediaSetting): MediaAlbum? {
        if (isAlbumEmpty)
            fetchMedia(setting)
        return albumMap[albumName]
    }

    private fun fetchMediaSync(setting: MediaSetting) {
        albumMap.clear()
        if (setting.mimeType == MimeType.VIDEO) {
            val internalUrl: Uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
            val externalUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            /**
             * FIXME 需改為併發
             * */
            fetchMediaVideoSync(setting, internalUrl)
            fetchMediaVideoSync(setting, externalUri)
            albumListRelay.accept(albumMap.values.toList())
        }
    }

    private fun fetchMediaVideoSync(setting: MediaSetting, uri: Uri) {
        val projection: Array<String>
        val selection: String
        val selectionArgs: Array<String>
        val sortOrder: String
        val cancellationSignal: CancellationSignal = CancellationSignal()
        //
        projection = arrayOf<String>(
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.BUCKET_DISPLAY_NAME else "bucket_display_name",
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.BUCKET_ID else "bucket_id",
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DATE_MODIFIED,
            MediaStore.Video.Media.DISPLAY_NAME,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration",
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.WIDTH,
            BaseColumns._ID,
        )
        selection = "${MediaStore.Video.Media.SIZE} > 0"
        selectionArgs = arrayOf<String>()
        sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"
        //
        val cursor = cxt.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder,
            cancellationSignal
        )
        //
        if (cursor?.moveToFirst() == true) {
            val bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.BUCKET_DISPLAY_NAME else "bucket_display_name")
            val bucketIdIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.BUCKET_ID else "bucket_id")
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration")
            val heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            //
            do {
                val bucketDisplayName = cursor.getString(bucketDisplayNameIndex)
                val bucketId = cursor.getInt(bucketIdIndex)
                val data = cursor.getString(dataIndex)
                val dateModified = cursor.getLong(dateModifiedIndex)
                val displayName = cursor.getString(displayNameIndex)
                val duration = cursor.getLong(durationIndex)
                val height = cursor.getInt(heightIndex)
                val mimeType = cursor.getString(mimeTypeIndex)
                val size = cursor.getLong(sizeIndex)
                val title = cursor.getString(titleIndex)
                val width = cursor.getInt(widthIndex)
                val id = cursor.getInt(idIndex)
                //
                val media = Media(
                    bucketDisplayName = bucketDisplayName,
                    bucketId = bucketId,
                    data = data,
                    dateModified = dateModified,
                    displayName = displayName,
                    duration = duration,
                    height = height,
                    mimeType = mimeType,
                    size = size,
                    title = title,
                    width = width,
                    id = id,
                )

                if (setting.videoMinDuration != null && setting.videoMinDuration > duration)
                    continue

                if (setting.videoMaxDuration != null && setting.videoMaxDuration < duration)
                    continue

                //add media album
                if (isAlbumEmpty) addAlbum(MEDIA_ALBUM_VIDEO, "", data)
                //add media
                addMedia(MEDIA_ALBUM_VIDEO, media)

                //add  media album in correspond
                val folder = File(data).parentFile?.absolutePath ?: ""
                addAlbum(bucketDisplayName, folder, data)
                //add media in correspond
                addMedia(bucketDisplayName, media)
            } while (cursor.moveToNext())
        }
        cursor?.close()
    }

    private fun addMedia(albumName: String, media: Media) {
        albumMap[albumName]!!.mediaList.add(media)
    }

    private fun addAlbum(albumName: String, folder: String, file: String) {
        albumMap[albumName] ?: MediaAlbum(
            albumName = albumName,
            folder = folder,
            file = file
        ).apply { albumMap[albumName] = this }
    }

}