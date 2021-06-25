package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * 分享 & 關於 - 資訊
 * @param shareLink >> 推廣連結
 * @param aboutCoaching >> 關於Coaching內容
 * @param commonProblemList >> 常見問題列表
 * @param privacyPolicy >> 隱私政策內容
 * @param contactUs >> 聯繫我們內容
 * @param reflectList >> 反映問題列表
 * */
data class PublicDataResponse(
    @SerializedName("share_link") val shareLink: String = "",
    @SerializedName("about_coaching") val aboutCoaching: String = "",
    @SerializedName("common_problem_list") val commonProblemList: List<CommonProblem> = emptyList(),
    @SerializedName("privacy_policy") val privacyPolicy: String = "",
    @SerializedName("contact_us") val contactUs: String = "",
    @SerializedName("reflect_list") val reflectList: List<Reflect> = emptyList(),
)

/**
 * 常見問題
 * @param id >> 問題Id
 * @param title >> 標題
 * @param content >> 內容
 * */
data class CommonProblem(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String = "",
    @SerializedName("content") val content: String = ""
)

/**
 * 反映問題
 * @param id >> 類型Id
 * @param name >> 類型名稱
 * */
data class Reflect(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)

