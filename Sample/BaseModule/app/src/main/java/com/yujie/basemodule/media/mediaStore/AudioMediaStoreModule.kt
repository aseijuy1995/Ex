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

class AudioMediaStoreModule(private val cxt: Context) : IMediaStoreModule, CoroutineScope {

    val albumMap = mutableMapOf<String, MediaAlbum>()

    val isAlbumEmpty: Boolean = albumMap.isEmpty()

    val albumListRelay: PublishRelay<List<MediaAlbum>> = PublishRelay.create()

    override fun fetchMedia(setting: MediaAlbumSetting): Completable = Completable.fromAction {
        fetchMediaSync(setting)
    }

    private fun fetchMediaSync(albumSetting: MediaAlbumSetting) {
        albumMap.clear()
        if (albumSetting.mimeType == MimeType.AUDIO) {
            val internalUrl: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
            val externalUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            runBlocking {
                launch(Dispatchers.IO) { fetchMediaAudioSync(albumSetting, internalUrl) }
                launch(Dispatchers.IO) { fetchMediaAudioSync(albumSetting, externalUri) }
            }
            albumListRelay.accept(albumMap.values.toList())
        }
    }

    private fun fetchMediaAudioSync(albumSetting: MediaAlbumSetting, uri: Uri) {
        val projection: Array<String> = arrayOf<String>(
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.AudioColumns.DATA else "_data",
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.DISPLAY_NAME,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.Media.DURATION else "duration",
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.TITLE,
            BaseColumns._ID,
        )
        val selection: String = "${MediaStore.Audio.Media.SIZE} > 0"
        val selectionArgs: Array<String> = arrayOf<String>()
        val sortOrder: String = "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"
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
            val bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.Media.DURATION else "duration")
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            do {
                val bucketDisplayName = cursor.getString(bucketDisplayNameIndex)
                val data = cursor.getString(dataIndex)
                val dateModified = cursor.getLong(dateModifiedIndex)
                val displayName = cursor.getString(displayNameIndex)
                val duration = cursor.getLong(durationIndex)
                val mimeType = cursor.getString(mimeTypeIndex)
                val size = cursor.getLong(sizeIndex)
                val title = cursor.getString(titleIndex)
                val id = cursor.getInt(idIndex)
                val media = Media(
                    bucketDisplayName = File(bucketDisplayName).parentFile.name,
                    data = data,
                    dateModified = dateModified,
                    displayName = displayName,
                    duration = duration,
                    mimeType = mimeType,
                    size = size,
                    title = title,
                    id = id,
                )

                if (albumSetting.audioMinDuration != null && albumSetting.audioMinDuration > duration)
                    continue

                if (albumSetting.audioMaxDuration != null && albumSetting.audioMaxDuration < duration)
                    continue

                //add media album
                if (isAlbumEmpty) addAlbum(MEDIA_ALBUM_AUDIO, "", data)
                //add media
                addMedia(MEDIA_ALBUM_AUDIO, media)

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