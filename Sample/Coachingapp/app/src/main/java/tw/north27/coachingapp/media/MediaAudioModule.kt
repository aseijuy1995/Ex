package tw.north27.coachingapp.media

import android.content.ContentValues
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
import java.io.FileNotFoundException

class MediaAudioModule(private val cxt: Context) : IMediaModule {

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
        if (setting.mimeType == MimeType.AUDIO) {
            val internalUrl: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI
            val externalUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            /**
             * FIXME 需改為併發
             * */
            fetchMediaAudioSync(setting, internalUrl)
            fetchMediaAudioSync(setting, externalUri)
            albumListRelay.accept(albumMap.values.toList())
        }
    }

    private fun fetchMediaAudioSync(setting: MediaSetting, uri: Uri) {
        val projection: Array<String>
        val selection: String
        val selectionArgs: Array<String>
        val sortOrder: String
        val cancellationSignal: CancellationSignal = CancellationSignal()
        //
        projection = arrayOf<String>(
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
        selection = "${MediaStore.Audio.Media.SIZE} > 0"
        selectionArgs = arrayOf<String>()
        sortOrder = "${MediaStore.Audio.Media.DATE_MODIFIED} DESC"
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
            val bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.Media.DURATION else "duration")
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            //
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
                //
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

                if (setting.audioMinDuration != null && setting.audioMinDuration > duration)
                    continue

                if (setting.audioMaxDuration != null && setting.audioMaxDuration < duration)
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

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------

//    override fun saveMedia(media: Media) {
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
//        val values = ContentValues(9)
//        values.put(MediaStore.Audio.Media.MIME_TYPE, media.mimeType)
//        values.put(MediaStore.Audio.Media.TITLE, media.title)
//        values.put(MediaStore.Audio.Media.DISPLAY_NAME, media.title)
//        values.put(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.Media.RELATIVE_PATH else "relative_path", "Music/Recordings/")
//        values.put(MediaStore.Audio.Media.DATE_ADDED, (System.currentTimeMillis() / 1000))
//        values.put(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) MediaStore.Audio.Media.NUM_TRACKS else "num_tracks", media.track)
//        values.put(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) MediaStore.Audio.Media.CAPTURE_FRAMERATE else "capture_framerate", media.sampleRate)
//        values.put(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) MediaStore.Audio.Media.BITRATE else "bitrate", media.bitRate)
//        values.put(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Audio.Media.DURATION else "duration", media.duration)
//        val contentUri = cxt.contentResolver.insert(uri, values)
//        contentUri?.let {
//            try {
//                val fileOutputStream = cxt.contentResolver.openOutputStream(it)
//                fileOutputStream?.write(media.byteArray)
//                fileOutputStream?.close()
//            } catch (e: FileNotFoundException) {
//                Timber.e(e)
//                e.printStackTrace()
//            }
//        } ?: throw NullPointerException("Can't find Content Uri!")
//    }

}