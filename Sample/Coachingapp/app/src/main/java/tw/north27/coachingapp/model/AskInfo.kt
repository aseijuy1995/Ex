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
 * @param askInfoList >> 提問室列表
 * */
data class AskInfos(
    @SerializedName("id") var id: Long,
    @SerializedName("self_act") var selfAct: String,
    @SerializedName("other_act") var otherAct: String,
    @SerializedName("education_level_id") var educationLevelId: Long,
    @SerializedName("grade_id") var gradeId: Long,
    @SerializedName("subject_id") var subjectId: Long,
    @SerializedName("unit_id") var unitId: Long,
    @SerializedName("ask_room_info_list") var askInfoList: List<AskInfo>,
)

