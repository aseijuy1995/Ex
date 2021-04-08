package tw.north27.coachingapp.media.mediaStore

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 媒體類型
 * */
@Parcelize
enum class MimeType(private val value: String) : Parcelable {
    ALL("all"),
    AUDIO("audio/"),
    IMAGE("image/"),
    VIDEO("video/");

    override fun toString() = value
}

/**
 * 媒體項目
 * */
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
    val track: Int = -1,//通道
    val sampleRate: Int = -1,//採樣率
    val bitRate: Int = -1,//比特率
    val byteArray: ByteArray? = null,//數據
    //
    var isChoice: Boolean = false//是否選取
) : Parcelable

/**
 * MediaAlbum設定
 * */
data class MediaAlbumSetting(
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
 * 媒體專輯項目
 * */
data class MediaAlbum(
    val albumName: String,
    val folder: String,
    val file: String
) {
    val mediaList = mutableListOf<Media>()
}



