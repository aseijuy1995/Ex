package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 提問資訊
 * @param id >> 提問id
 * @param sender >> 提問者資訊
 * @param receiver >> 接收者資訊
 * @param askType >> 提問類型
 * @param askInfo >> 提問資訊
 * @param isRead >> 讀取狀態
 * @param sendTime >> 發送時間
 * @param displayMsg >> 列表顯示訊息
 * */
@Parcelize
data class AskInfo(
    @SerializedName("id") val id: Long,
    @SerializedName("sender") var sender: UserInfo,
    @SerializedName("receiver") var receiver: UserInfo,
    @SerializedName("ask_type") var askType: AskType,
    @SerializedName("text") val text: String? = null,
    @SerializedName("img_list") val imgList: List<AskImage> = emptyList(),
    @SerializedName("audio_list") val audioList: List<AskAudio> = emptyList(),
    @SerializedName("video_list") val videoList: List<AskVideo> = emptyList(),
    @SerializedName("is_read") var isRead: Boolean,
    @SerializedName("send_time") val sendTime: Date,
    @SerializedName("display_msg") val displayMsg: String,
) : Parcelable

/**
 * 提問類型
 * @param TEXT >> 文字
 * @param IMAGE >> 圖片
 * @param AUDIO >> 音訊
 * @param VIDEO >> 影片
 * */
enum class AskType {
    TEXT,
    IMAGE,
    AUDIO,
    VIDEO
}

/**
 * 圖片
 * @param url >> 圖片連結
 * */
@Parcelize
data class AskImage(
    @SerializedName("url") val url: String
) : Parcelable

/**
 * 音訊
 * @param url >> 音訊連結
 * @param time >> 音訊時長
 * */
@Parcelize
data class AskAudio(
    @SerializedName("url") val url: String,
    @SerializedName("time") val time: Long
) : Parcelable

/**
 * 影片
 * @param url >> 影片連結
 * @param time >> 影片時長
 * */
@Parcelize
data class AskVideo(
    @SerializedName("url") val url: String,
    @SerializedName("time") val time: Long
) : Parcelable