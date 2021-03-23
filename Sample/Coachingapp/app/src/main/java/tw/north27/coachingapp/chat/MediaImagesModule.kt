package tw.north27.coachingapp.chat

import android.content.Context
import android.net.Uri
import android.os.CancellationSignal
import android.provider.BaseColumns
import android.provider.MediaStore
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.myapplication.Media
import com.yujie.myapplication.MediaAlbum
import com.yujie.myapplication.MediaSetting
import com.yujie.myapplication.MimeType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class MediaImagesModule(private val cxt: Context) : IMediaImagesModule {

    val albumMap = mutableMapOf<String, MediaAlbum>()

    val isAlbumEmpty: Boolean = albumMap.isEmpty()

    val albumListRelay: PublishRelay<List<MediaAlbum>> = PublishRelay.create()

    override fun fetchMediaImages(setting: MediaSetting): Completable = Completable.fromAction {
        fetchMediaSync(setting)
    }

    override fun getMediaAlbum(setting: MediaSetting): PublishRelay<List<MediaAlbum>> {
        if (isAlbumEmpty)
            fetchMediaImages(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay
    }

    override fun getAlbumFromName(albumName: String, setting: MediaSetting): Observable<MediaAlbum?> {
        if (isAlbumEmpty)
            fetchMediaImages(setting)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        return albumListRelay.map { it.find { it.albumName == albumName } }
    }

    override fun getAlbumFromNameSync(albumName: String, setting: MediaSetting): MediaAlbum? {
        if (isAlbumEmpty)
            fetchMediaImages(setting)
        return albumMap[albumName]
    }

    private fun fetchMediaSync(setting: MediaSetting) {
        albumMap.clear()
        if (setting.mimeType == MimeType.IMAGES)
            fetchMediaExternalImages()
    }

    private fun fetchMediaExternalImages() {
        val internalUrl: Uri
        val externalUri: Uri
        val projection: Array<String>
        val selection: String
        val selectionArgs: Array<String>
        val sortOrder: String
        val cancellationSignal: CancellationSignal = CancellationSignal()
        //
        internalUrl = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        externalUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        projection = arrayOf<String>(
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name",
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_ID else "bucket_id",
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DISPLAY_NAME,
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.DURATION else "duration",
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.WIDTH,
            BaseColumns._ID,
        )
        selection = "${MediaStore.Images.Media.SIZE} > 0"
        selectionArgs = arrayOf<String>()
        sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"
        //
        val cursor = cxt.contentResolver.query(
//            externalUri,
            internalUrl,
            projection,
            selection,
            selectionArgs,
            sortOrder,
            cancellationSignal
        )
        //
        if (cursor?.moveToFirst() == true) {
            val bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_DISPLAY_NAME else "bucket_display_name")
            val bucketIdIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.BUCKET_ID else "bucket_id")
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
//            val durationIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Images.Media.DURATION else "duration",)
            val heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE)
            val widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val idIndex = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            //
            do {
                val bucketDisplayName = cursor.getString(bucketDisplayNameIndex)
                val bucketId = cursor.getInt(bucketIdIndex)
                val data = cursor.getString(dataIndex)
                val dateModified = cursor.getLong(dateModifiedIndex)
                val displayName = cursor.getString(displayNameIndex)
//                val duration = cursor.getLong(durationIndex)
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
//                    duration = duration,
                    duration = 0,
                    height = height,
                    mimeType = mimeType,
                    size = size,
                    title = title,
                    width = width,
                    id = id,
                )
                //add media album
                if (isAlbumEmpty) addAlbum(MEDIA_ALBUM_IMAGES, data)
                //add media
                addMedia(MEDIA_ALBUM_IMAGES, media)
                Timber.d("bucketDisplayName = $bucketDisplayName")
            } while (cursor.moveToNext())
        }
        cursor?.close()
        Timber.d("albumMap.values.toList() =${albumMap.values.toList()}")
        albumListRelay.accept(albumMap.values.toList())
    }

    private fun addMedia(albumName: String, media: Media) {
        albumMap[albumName]!!.mediaList.add(media)
    }

    private fun addAlbum(albumName: String, data: String) {
        albumMap[albumName] ?: MediaAlbum(
            albumName = albumName,
            folder = "",
            file = data
        ).apply { albumMap[albumName] = this }
    }


}