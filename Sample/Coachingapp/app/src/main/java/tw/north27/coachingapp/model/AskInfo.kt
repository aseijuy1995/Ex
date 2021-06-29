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
 * @param text >> 文字
 * @param imgList >> 圖片
 * @param audioList >> 音訊
 * @param videoList >> 影片
 * @param isRead >> 讀取狀態 - true:已讀 / false:未讀
 * @param unReadCount >> 未讀數量
 * @param sendTime >> 發送時間
 * @param msg >> 列表顯示訊息
 * */
@Parcelize
data class AskInfo(
    @SerializedName("id") val id: Long,
    //
//    @SerializedName("is_self_send") var isSelfSend: Boolean,
//    @SerializedName("sender") var sender: UserInfo,
//    @SerializedName("receiver") var receiver: UserInfo,
    @SerializedName("user_info") var userInfo: UserInfo,
    //
    @SerializedName("ask_type") var askType: AskType,
    @SerializedName("text") val text: String? = null,
    @SerializedName("img_list") val imgList: List<AskImage> = emptyList(),
    @SerializedName("audio_list") val audioList: List<AskAudio> = emptyList(),
    @SerializedName("video_list") val videoList: List<AskVideo> = emptyList(),
    //
    @SerializedName("is_sound") var isSound: Boolean,
    @SerializedName("is_read") var isRead: Boolean,
    @SerializedName("un_read_count") var unReadCount: Int,
    @SerializedName("send_time") val sendTime: Date,
    @SerializedName("unit_id") var unitId: Long,

    @SerializedName("msg") val msg: String,
) : Parcelable {
//    //處理後的時間格是
//    val sendDateTimeFormat: String
//        get() {
////            val calendar = Calendar.getInstance()
////            val year = calendar[Calendar.YEAR]
////            val month = calendar[Calendar.MONTH] + 1
////            val day = calendar[Calendar.DAY_OF_MONTH]
////            calendar.time = sendTime
////            val year = calendar[Calendar.YEAR]
////            val month = calendar[Calendar.MONTH] + 1
////            val day = calendar[Calendar.DAY_OF_MONTH]
//            ""
//        }
}

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