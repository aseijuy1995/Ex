package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import tw.north27.coachingapp.model.response.ClientInfo
import java.util.*

/**
 * 提問室資訊
 * @param id >> 提問室id
 * @param otherClientInfo >> 對方用戶資訊
 * @param educationLevelId >> 教育程度Id
 * @param gradeId >> 年級Id
 * @param subjectId >> 科目Id
 * @param unitId >> 單元/類型Id
 * @param isPush >> 推播開關
 * @param isSound >> 聲音開關
 * @param unreadNum >> 未讀數量
 * @param askRoomInfo >> 最新提問資訊
 * */
@Parcelize
data class AskRoom(
    @SerializedName("id") var id: Long,
    @SerializedName("other_client_info") var otherClientInfo: ClientInfo,
    @SerializedName("education_level_id") var educationLevelId: Long,
    @SerializedName("grade_id") var gradeId: Long,
    @SerializedName("subject_id") var subjectId: Long,
    @SerializedName("unit_id") var unitId: Long,
    @SerializedName("is_push") var isPush: Boolean,
    @SerializedName("is_sound") var isSound: Boolean,
    @SerializedName("unread_num") var unreadNum: Int,
    @SerializedName("ask_info") var askRoomInfo: AskRoomInfo? = null,
) : Parcelable

/**
 * 提問資訊
 * @param id >> 提問id
 * @param senderId >> 發送者帳號
 * @param receiverId >> 接收者帳號
 * @param askType >> 提問類型
 * @param text >> 文字
 * @param imgList >> 圖片列表
 * @param audioList >> 音訊列表
 * @param videoList >> 影片列表
 * @param isRead >> 是否讀取
 * @param sendTime >> 發送時間
 * */
@Parcelize
data class AskRoomInfo(
    @SerializedName("id") var id: Long,
    @SerializedName("sender_id") var senderId: String,
    @SerializedName("receiver_id") var receiverId: String,
    @SerializedName("ask_type") var askType: AskType,
    @SerializedName("text") var text: String? = null,
    @SerializedName("img_list") var imgList: List<AskImage> = emptyList(),
    @SerializedName("audio_list") var audioList: List<AskAudio> = emptyList(),
    @SerializedName("video_list") var videoList: List<AskVideo> = emptyList(),
    @SerializedName("is_read") var isRead: Boolean,
    @SerializedName("send_time") var sendTime: Date,
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
    @SerializedName("id") val id: Long,
    @SerializedName("url") val url: String
) : Parcelable

/**
 * 音訊
 * @param url >> 音訊連結
 * @param time >> 音訊時長
 * */
@Parcelize
data class AskAudio(
    @SerializedName("id") val id: Long,
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
    @SerializedName("id") val id: Long,
    @SerializedName("url") val url: String,
    @SerializedName("time") val time: Long
) : Parcelable