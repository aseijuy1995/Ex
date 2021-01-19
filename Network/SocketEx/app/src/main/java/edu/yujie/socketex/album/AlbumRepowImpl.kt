package edu.yujie.socketex.album

import android.content.Context
import android.provider.MediaStore
import edu.yujie.socketex.bean.MimeType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.io.File

const val ALL_MEDIA_ALBUM_NAME = "ALL_MEDIA_ALBUM_NAME"
const val KEY_MEDIA_LIST = "KEY_MEDIA_LIST"

class AlbumRepowImpl(private val context: Context) : IAlbumRepow {
    private val albumSubject = BehaviorSubject.create<List<AlbumItem>>()

    private val albumItemMapping = mutableMapOf<String, AlbumItem>()

    override fun fetchAlbums(setting: AlbumSetting?): Completable = Completable.fromAction {
        fetchAlbumSync(setting)
    }

    private fun fetchAlbumSync(setting: AlbumSetting?) {
        albumItemMapping.clear()
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name",
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            MediaStore.MediaColumns.SIZE,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration"
        )
//        val selection = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR " + "${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND " + "${MediaStore.MediaColumns.SIZE} > 0"
//        val selectionArgs = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(), MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString())
        val selection = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND " + "${MediaStore.MediaColumns.SIZE} > 0"
        val selectionArgs = arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString())
        val sortOrder = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
//        val cursorLoader = CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder)
//        val cursor = cursorLoader.loadInBackground()
        //
        if (cursor?.moveToFirst() == true) {
            val indexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val indexBucketDisplayName =
                cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name")
            val indexDisplayName = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
            val indexDateModified = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)
            val indexMimeType = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
            val indexSize = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)
//            val indexDuration = cursor.getColumnIndex(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Video.Media.DURATION else "duration")
            val indexDuration = cursor.getColumnIndex(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.DURATION else "duration")
            val indexWidth = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.WIDTH)
            val indexHeight = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.HEIGHT)

            do {
                val data = cursor.getString(indexData)
                val bucketDisplayName = cursor.getString(indexBucketDisplayName)
                val displayName = cursor.getString(indexDisplayName)
                val dateModified = cursor.getLong(indexDateModified)
                val mimeType = cursor.getString(indexMimeType)
                val size = cursor.getLong(indexSize)
                val duration = cursor.getLong(indexDuration)
                val width = cursor.getInt(indexWidth)
                val height = cursor.getInt(indexHeight)
                //
                if (data.isNullOrEmpty() || mimeType.isNullOrEmpty()) continue
                val file = File(data)
                if (!file.exists() || !file.isFile) continue

                if (MimeType.IMAGE == setting?.mimeType && !mimeType.startsWith(MimeType.IMAGE.toString())) continue
                if (MimeType.VIDEO == setting?.mimeType && !mimeType.startsWith(MimeType.VIDEO.toString())) continue
                if (mimeType.startsWith(MimeType.IMAGE.toString())) {
                    if (setting?.imageMaxSize != null) {
                        if (setting.imageMaxSize!! > size) continue
                    }
                }
                if (mimeType.startsWith(MimeType.VIDEO.toString())) {
                    if (setting?.videoMinSecond != null && setting.videoMinSecond!!.times(1000) > duration) {
                        continue
                    }
                    if (setting?.videoMaxSecond != null && setting.videoMaxSecond!!.times(1000) < duration) {
                        continue
                    }
                }
                val media = Media(data, displayName, bucketDisplayName, size, dateModified, duration, width, height)
                //
                if (isEmpty()) {
                    addAlbumItem(ALL_MEDIA_ALBUM_NAME, "", data)
                }
                addMediaToAlbum(ALL_MEDIA_ALBUM_NAME, media)

                val folder = file.parentFile?.absolutePath ?: ""
                addAlbumItem(bucketDisplayName, folder, data)
                addMediaToAlbum(bucketDisplayName, media)

            } while (cursor.moveToNext())
        }
        cursor?.close()
        albumSubject.onNext(albumItemMapping.values.toList())
    }

    override fun getAlbums(setting: AlbumSetting?): BehaviorSubject<List<AlbumItem>> {
        if (isEmpty()) {
            fetchAlbums(setting)
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
        return albumSubject
    }

    override fun getAlbumItem(name: String, setting: AlbumSetting?): Observable<AlbumItem> {
        if (isEmpty()) {
            fetchAlbums(setting)
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
        return albumSubject.map { albumItemMapping[name] }
    }

    override fun getAlbumItemSync(name: String, settings: AlbumSetting?): AlbumItem? {
        if (isEmpty()) {
            fetchAlbumSync(settings)
        }
        return albumItemMapping[name]
    }

    private fun addMediaToAlbum(albumName: String, media: Media) = albumItemMapping[albumName]?.mediaList?.add(media)

    private fun addAlbumItem(name: String, folder: String, coverImagePath: String) = albumItemMapping[name]?.run {
        albumItemMapping[name] = AlbumItem(name = name, folder = folder, coverImagePath = coverImagePath)
    }

    private fun isEmpty() = albumItemMapping.keys.isEmpty()
}