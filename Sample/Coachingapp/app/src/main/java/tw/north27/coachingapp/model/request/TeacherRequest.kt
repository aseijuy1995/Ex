package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 請求老師列表
 * @param clientId >> 用戶Id
 * @param educationLevelId >> 教育id
 * @param gradeId >> 年級id
 * @param subjectId >> 科目id
 * @param unitId >> 單元id
 * @param index >> 索引
 * @param num >> 筆數
 * */
data class TeacherRequest(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("education_level_id") val educationLevelId: Long? = null,
    @SerializedName("grade_id") val gradeId: Long? = null,
    @SerializedName("subject_id") val subjectId: Long? = null,
    @SerializedName("unit_id") val unitId: Long? = null,
    @SerializedName("index") val index: Int,
    @SerializedName("num") val num: Int
)