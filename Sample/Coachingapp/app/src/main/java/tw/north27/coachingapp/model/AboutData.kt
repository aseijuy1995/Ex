package tw.north27.coachingapp.model

import com.google.gson.annotations.SerializedName

/**
 * 關於數據
 * @param shareLinkContent >> 推廣連結
 * @param aboutCoaching >> 關於Coaching內容
 * @param commonProblemList >> 常見問題列表
 * @param privacyPolicy >> 隱私政策內容
 * @param contactUs >> 聯繫我們內容
 * */
data class AboutData(
    @SerializedName("share_link_content") val shareLinkContent: String = "",
    @SerializedName("about_coaching_content") val aboutCoachingContent: String = "",
    @SerializedName("common_problem_list") val commonProblemList: List<CommonProblem> = emptyList(),
    @SerializedName("privacy_policy_content") val privacyPolicyContent: String = "",
    @SerializedName("contact_us_content") val contactUsContent: String = "",
)

/**
 * 常見問題
 * @param id >> 問題Id
 * @param title >> 標題
 * @param content >> 內容
 * */
data class CommonProblem(
    val id: Long,
    val title: String = "",
    val content: String = ""
)

