package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * 提問資訊
 * @param id >> 提問id
 * @param senderUser >> 發送者資訊
 * @param receiverUser >> 接收者資訊
 *
 * @param askType >> 提問類型
 * @param text >> 文字
 * @param imgList >> 圖片
 * @param audioList >> 音訊
 * @param videoList >> 影片
 * @param isSound >> 聲音開關
 * @param unReadNum >> 未讀數量
 * @param sendTime >> 發送時間
 * @param msg >> 列表顯示訊息
 * @param educationId >> 教育Id
 * @param gradeId >> 年級Id
 * @param subjectId >> 科目Id
 * @param unitId >> 單元Id
 * */
@Parcelize
data class AskInfo(
    @SerializedName("id") var id: Long,
    @SerializedName("sender__user_info") var senderUser: UserInfo,
    @SerializedName("receiver_user_info") var receiverUser: UserInfo,
    @SerializedName("ask_type") var askType: AskType,
    @SerializedName("text") var text: String? = null,
    @SerializedName("img_list") var imgList: List<AskImage> = emptyList(),
    @SerializedName("audio_list") var audioList: List<AskAudio> = emptyList(),
    @SerializedName("video_list") var videoList: List<AskVideo> = emptyList(),
    @SerializedName("is_sound") var isSound: Boolean,
    @SerializedName("un_read_num") var unReadNum: Int,
    @SerializedName("send_time") var sendTime: Date,
    @SerializedName("msg") var msg: String,
    //
    @SerializedName("education_id") var educationId: Long,
    @SerializedName("grade_id") var gradeId: Long,
    @SerializedName("subject_id") var subjectId: Long,
    @SerializedName("unit_id") var unitId: Long,
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