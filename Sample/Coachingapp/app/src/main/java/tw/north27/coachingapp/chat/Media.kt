package tw.north27.coachingapp.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val ALL_MEDIA_ALBUM_NAME = "ALL_MEDIA_ALBUM_NAME"

/**
 * 設定媒體
 * */
data class MediaSetting(
    val mimeType: MimeType = MimeType.ALL,

    val isMultipleSelection: Boolean = false,
    val maxSelectionCount: Int = 6,
    val imageMaxSize: Long? = 0L,//圖片大小
    val videoMaxDuration: Int? = null,//視頻最大時長
    val videoMinDuration: Int? = null,//視頻最小時長
    val audioMaxDuration: Int? = null,//音訊最大時長
    val audioMinDuration: Int? = null//音訊最小時長
)

/**
 * 媒體類別
 * */
enum class MimeType(private val mimeType: String) {
    ALL("all"),
    AUDIO("audio"),
    IMAGES("image"),
    VIDEO("video");

    override fun toString() = mimeType
}

/**
 * 專輯項目
 * */
data class MediaAlbum(
    val name: String,
    val folder: String,
    val covertPath: String
) {
    val mediaList = mutableListOf<Media>()
}

@Parcelize
data class Media(
    val id: Int,
    val bucketDisplayName: String,
    val bucketId: Int,
    val dateModified: Long,
    val displayName: String,
    val duration: Long,
    val height: Int,
    val mimeType: String,
    val data: String,
    val size: Long,
    val title: String,
    val width: Int,
    //
    var isSelect: Boolean = false
) : Parcelable

