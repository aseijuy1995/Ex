package tw.north27.coachingapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * @param id >> 房間id
 * @param selfAct >> 教育Id
 * @param otherAct >> 教育Id
 * @param educationLevelId >> 教育Id
 * @param gradeId >> 年級Id
 * @param subjectId >> 科目Id
 * @param unitId >> 單元Id
 * @param askRoomInfoList >> 提問室列表
 * */
data class AskRoom(
    @SerializedName("id") var id: Long,
    @SerializedName("self_act") var selfAct: String,
    @SerializedName("other_act") var otherAct: String,
    @SerializedName("education_level_id") var educationLevelId: Long,
    @SerializedName("grade_id") var gradeId: Long,
    @SerializedName("subject_id") var subjectId: Long,
    @SerializedName("unit_id") var unitId: Long,
    @SerializedName("ask_room_info_list") var askRoomInfoList: List<AskRoomInfo>,
)

/**
 * 提問資訊
 * @param sendAct >> 發送者帳號
 * @param askType >> 提問類型
 * @param text >> 文字
 * @param imgList >> 圖片
 * @param audioList >> 音訊
 * @param videoList >> 影片
 * @param isRead >> 是否讀取
 * @param sendTime >> 發送時間
 * */
@Parcelize
data class AskRoomInfo(
    @SerializedName("sender_account") var sendAct: String,
    @SerializedName("ask_type") var askType: AskType,
    @SerializedName("text") var text: String? = null,
    @SerializedName("img_list") var imgList: List<AskImage> = emptyList(),
    @SerializedName("audio_list") var audioList: List<AskAudio> = emptyList(),
    @SerializedName("video_list") var videoList: List<AskVideo> = emptyList(),
    @SerializedName("is_read") var isRead: Boolean,
    @SerializedName("send_time") var sendTime: Date,
) : Parcelable