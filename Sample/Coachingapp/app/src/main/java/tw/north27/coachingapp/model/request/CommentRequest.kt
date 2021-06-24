package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

/**
 * 評論列表請求Body
 * @param account >> 帳號
 * @param score >> 評分
 * @param educationId >> 教育id
 * @param gradeId >> 年級id
 * @param subjectId >> 科目id
 * @param unitId >> 單元id
 * @param index >> 索引
 * @param num >> 筆數
 * */
data class CommentRequest(
    @SerializedName("account") val account: String,
    @SerializedName("score") val score: Double? = null,
    @SerializedName("education_id") val educationId: Long? = null,
    @SerializedName("grade_id") val gradeId: Long? = null,
    @SerializedName("subject_id") val subjectId: Long? = null,
    @SerializedName("unit_id") val unitId: Long? = null,
    @SerializedName("index") val index: Int,
    @SerializedName("num") val num: Int
)