package tw.north27.coachingapp.media.mediaStore

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
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

class ImageMediaStoreModule(private val cxt: Context) : IMediaStoreModule, CoroutineScope {

    val albumMap = mutableMapOf<String, MediaAlbum>()

    val isAlbumEmpty: Boolean = albumMap.isEmpty()

    val albumListRelay: PublishRelay<List<MediaAlbum>> = PublishRelay.create()

    override fun fetchMedia(setting: MediaAlbumSetting): Completable = Completable.fromAction {
        fetchMediaSync(setting)
    }

    private fun fetchMediaSync(albumSetting: MediaAlbumSetting) {
        albumMap.clear()
        if (albumSetting.mimeType == MimeType.IMAGE) {
            val internalUrl: Uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
            val externalUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            runBlocking {
                launch(Dispatchers.IO) { fetchMediaImageSync(albumSetting, internalUrl) }
                launch(Dispatchers.IO) { fetchMediaImageSync(albumSetting, externalUri) }
            }
            albumListRelay.accept(albumMap.values.toList())
        }
    }

    private fun fetchMediaImageSync(albumSetting: MediaAlbumSetting, uri: Uri) {
        val projection: Array<String> = arrayOf<String>(
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name",
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_ID else "bucket_id",
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.WIDTH,
            BaseColumns._ID,
        )
        val selection: String = "${MediaStore.Images.Media.SIZE} > 0"
        val selectionArgs: Array<String> = arrayOf<String>()
        val sortOrder: String = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        val cancellationSignal: CancellationSignal = CancellationSignal()
        val cursor = cxt.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder,
            cancellationSignal
        )
        if (cursor?.moveToFirst() == true) {
            val bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name")
            val bucketIdIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_ID else "bucket_id")
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
            val widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            do {
                val bucketDisplayName = cursor.getString(bucketDisplayNameIndex)
                val bucketId = cursor.getInt(bucketIdIndex)
                val data = cursor.getString(dataIndex)
                val dateModified = cursor.getLong(dateModifiedIndex)
                val displayName = cursor.getString(displayNameIndex)
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
                    height = height,
                    mimeType = mimeType,
                    size = size,
                    title = title,
                    width = width,
                    id = id,
                )

                if (albumSetting.imageMinSize != null && albumSetting.imageMinSize > size)
                    continue

                if (albumSetting.imageMaxSize != null && albumSetting.imageMaxSize < size)
                    continue

                //add media album
                if (isAlbumEmpty) addAlbum(MEDIA_ALBUM_IMAGE, "", data)
                //add media
                addMedia(MEDIA_ALBUM_IMAGE, media)

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

    override fun getMediaAlbum(setting: MediaAlbumSetting): PublishRelay<List<MediaAlbum>> {
        if (isAlbumEmpty)
            fetchMedia(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay
    }

    override fun getAlbumFromName(albumName: String, setting: MediaAlbumSetting): Observable<MediaAlbum?> {
        if (isAlbumEmpty)
            fetchMedia(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay.map { it.find { it.albumName == albumName } }
    }

    override fun getAlbumFromNameSync(albumName: String, setting: MediaAlbumSetting): MediaAlbum? {
        if (isAlbumEmpty)
            fetchMedia(setting)
        return albumMap[albumName]
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

}