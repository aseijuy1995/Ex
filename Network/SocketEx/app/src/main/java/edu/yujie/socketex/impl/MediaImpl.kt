package edu.yujie.socketex.impl

import android.content.Context
import android.net.Uri
import android.os.CancellationSignal
import android.provider.MediaStore
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.ALL_MEDIA_ALBUM_NAME
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.inter.IMedia
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

class MediaImpl(private val context: Context) : IMedia {
    private val mediaAlbumItemBehaviorRelay = BehaviorRelay.create<List<MediaAlbumItem>>()

    override fun fetchMediaDatas(setting: MediaSetting?): Completable = Completable.fromAction {
        fetchMediaDatasSync(setting)
    }

    private fun fetchMediaDatasSync(setting: MediaSetting?) {
        val uri: Uri = MediaStore.Files.getContentUri("external")
        val projection: Array<String> =
            arrayOf<String>(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
//                MediaStore.Files.FileColumns.ALBUM,//檢索有關數據源專輯標題的信息
//                MediaStore.Files.FileColumns.ALBUM_ARTIST,//檢索有關與數據源關聯的表演者或藝術家的信息
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME else "bucket_display_name",//媒體項目的主存儲桶顯示名稱
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.BUCKET_ID else "bucket_id",//媒體項目的主要存儲區ID
//                MediaStore.Files.FileColumns.CAPTURE_FRAMERATE,//原始捕獲幀率
                MediaStore.Files.FileColumns.DATE_MODIFIED,//文件的最後修改時間
                MediaStore.Files.FileColumns.DISPLAY_NAME,//媒體項目的顯示名稱
//                MediaStore.Files.FileColumns.DOCUMENT_ID,//文檔ID-GUID
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.DURATION else "duration",//播放持續時間
//                MediaStore.Files.FileColumns.GENRE,//檢索數據源的內容類型或體裁
                MediaStore.Files.FileColumns.HEIGHT,//高度
//                MediaStore.Files.FileColumns.INSTANCE_ID,//實例ID-GUID
                MediaStore.Files.FileColumns.MIME_TYPE,//媒體項目的MIME類型
//                MediaStore.Files.FileColumns.NUM_TRACKS,//音軌數量
//                MediaStore.Files.FileColumns.ORIGINAL_DOCUMENT_ID,//原始文檔ID-GUID
//                MediaStore.Files.FileColumns.OWNER_PACKAGE_NAME,//貢獻此媒體的軟件包名稱
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.RELATIVE_PATH else "relative_path",//媒體項目在存儲設備中的相對路徑
//                MediaStore.Files.FileColumns.RESOLUTION,//計算值相結合WIDTH，並HEIGHT 為用戶呈現的字符串
                MediaStore.Files.FileColumns.SIZE,//文件的長度
                MediaStore.Files.FileColumns.TITLE,//檢索數據源標題。
//                MediaStore.Files.FileColumns.VOLUME_NAME,//媒體項目的特定存儲設備的捲名
                MediaStore.Files.FileColumns.WIDTH//寬度
//                MediaStore.Files.FileColumns.WRITER,//作者的信息
//                MediaStore.Files.FileColumns.XMP,//
//                MediaStore.Files.FileColumns.YEAR//數據源的年份
            )

        val selection: String = "(${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?) AND ${MediaStore.Files.FileColumns.SIZE} > 0"
        val selectionArgs: Array<String> = arrayOf<String>(
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString()
//            MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_DOCUMENT.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_PLAYLIST.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_SUBTITLE.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE_NONE.toString(),
//            MediaStore.Files.FileColumns.MEDIA_TYPE
        )
        val sortOrder: String = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
        val cancellationSignal: CancellationSignal = CancellationSignal()

        val cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal)
        if (cursor?.moveToFirst() == true) {
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            val bucketDisplayNameIndex =
                cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME else "bucket_display_name")
            val bucketIdIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.BUCKET_ID else "bucket_id")
            val dateModifiedIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val durationIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.DURATION else "duration")
            val heightIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.HEIGHT)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
//            val relativePathIndex = cursor.getColumnIndexOrThrow(if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) MediaStore.Files.FileColumns.RELATIVE_PATH else "relative_path")
            val sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE)
            val widthIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.WIDTH)

            do {
                val id = cursor.getInt(idIndex)
                val data = cursor.getString(dataIndex)
                val bucketDisplayName = cursor.getString(bucketDisplayNameIndex)
                val bucketId = cursor.getInt(bucketIdIndex)
                val dateModified = cursor.getLong(dateModifiedIndex)
                val displayName = cursor.getString(displayNameIndex)
                val duration = cursor.getLong(durationIndex)
                val height = cursor.getInt(heightIndex)
                val mimeType = cursor.getString(mimeTypeIndex)
//                val relativePath = cursor.getString(relativePathIndex)
                val size = cursor.getLong(sizeIndex)
                val title = cursor.getString(titleIndex)
                val width = cursor.getInt(widthIndex)

                val media = Media(
                    id = id,
                    bucketDisplayName = bucketDisplayName,
                    bucketId = bucketId,
                    dateModified = dateModified,
                    displayName = displayName,
                    duration = duration,
                    height = height,
                    mimeType = mimeType,
//                    relativePath = relativePath,
                    relativePath = data,
                    size = size,
                    title = title,
                    width = width
                )
                val file = File(data)
                //
                if (isEmpty) addMedia(ALL_MEDIA_ALBUM_NAME, "", data)
                addMediaAlbumItem(ALL_MEDIA_ALBUM_NAME, media)
                //
                val folder = file.parentFile?.absolutePath ?: ""
                addMedia(name = bucketDisplayName, folder = folder, covertPath = data)
                addMediaAlbumItem(bucketDisplayName, media)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        mediaAlbumItemBehaviorRelay.accept(mediaItemMap.values.toList())
    }

    private val mediaItemMap = mutableMapOf<String, MediaAlbumItem>()

    private val isEmpty: Boolean = mediaItemMap.keys.isEmpty()

    private fun addMediaAlbumItem(albumName: String, media: Media) {
        mediaItemMap[albumName]?.mediaList?.add(media)
    }

    private fun addMedia(name: String, folder: String, covertPath: String) {
        mediaItemMap[name] ?: MediaAlbumItem(name = name, folder = folder, covertPath = covertPath).also {
            mediaItemMap[name] = it
        }
    }

    override fun getMediaItems(setting: MediaSetting?): BehaviorRelay<List<MediaAlbumItem>> {
        if (isEmpty)
            fetchMediaDatas(setting = setting)
                .subscribeOn(Schedulers.io())
                .subscribe()

        return mediaAlbumItemBehaviorRelay
    }

    override fun getMediaItem(name: String, setting: MediaSetting?): Observable<MediaAlbumItem> {
        if (isEmpty)
            fetchMediaDatas(setting = setting)
                .subscribeOn(Schedulers.io())
                .subscribe()

        return mediaAlbumItemBehaviorRelay.map { mediaItemMap[name] }

    }

    override fun getMediaItemSync(name: String, setting: MediaSetting?): MediaAlbumItem? {
        if (isEmpty) fetchMediaDatas(setting = setting)
        return mediaItemMap[name]
    }
}