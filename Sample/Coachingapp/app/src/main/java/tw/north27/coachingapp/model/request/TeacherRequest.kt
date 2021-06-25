package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 請求 - 老師列表
 * @param account >> 帳號
 * @param educationId >> 教育id
 * @param gradeId >> 年級id
 * @param subjectId >> 科目id
 * @param unitId >> 單元id
 * @param index >> 索引
 * @param num >> 筆數
 * */
data class TeacherRequest(
    @SerializedName("account") val account: String,
    @SerializedName("education_id") val educationId: Long? = null,
    @SerializedName("grade_id") val gradeId: Long? = null,
    @SerializedName("subject_id") val subjectId: Long? = null,
    @SerializedName("unit_id") val unitId: Long? = null,
    @SerializedName("index") val index: Int,
    @SerializedName("num") val num: Int
)