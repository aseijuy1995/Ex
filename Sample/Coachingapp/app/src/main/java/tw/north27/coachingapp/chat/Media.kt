package tw.north27.coachingapp.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 設定媒體
 * */
data class MediaSetting(
    val mimeType: MimeType = MimeType.ALL,
    val isMultipleChoice: Boolean = false,//多選
    val maxCount: Int = 6,//最多數量
    //audio
    val audioMinDuration: Int? = null,//音訊最小時長
    val audioMaxDuration: Int? = null,//音訊最大時長
    //image
    val imageMinSize: Long? = null,//圖片最小（size）
    val imageMaxSize: Long? = null,//圖片最大（size）
    //video
    val videoMinDuration: Int? = null,//影音最小時長（ms）
    val videoMaxDuration: Int? = null,//影音最大時長（ms）
)

/**
 * 媒體類別
 * */
enum class MimeType(private val type: String) {
    ALL("all"),
    AUDIO("audio"),
    IMAGES("image"),
    VIDEO("video");

    override fun toString() = type
}

/**
 * 專輯項目
 * */
data class MediaAlbum(
    val albumName: String,
    val folder: String,
    val file: String
) {
    val mediaList = mutableListOf<Media>()
}

@Parcelize
data class Media(
    val bucketDisplayName: String = "",
    val bucketId: Int = 0,
    val data: String = "",
    val dateModified: Long,
    val displayName: String = "",
    val duration: Long = 0,
    val height: Int = 0,
    val mimeType: String = "",
    val size: Long = 0,
    val title: String = "",
    val width: Int = 0,
    val id: Int,
    //
    var isSelect: Boolean = false
) : Parcelable

