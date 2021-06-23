package tw.north27.coachingapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * App基礎設定
 * @param appCode >> 狀態碼 運行中(1000)、維護中(1001)
 * @param runInfo >> 運行資訊
 * @param maintainInfo >> 維護資訊
 * */
data class AppConfig(
    @SerializedName("app_code") val appCode: Int,//AppCode
    @SerializedName("run_info") val runInfo: RunInfo? = null,
    @SerializedName("maintain_info") val maintainInfo: MaintainInfo? = null,
)

/**
 * App狀態碼
 * @param RUN >> 運行中(1000)
 * @param MAINTAIN >> 維護中(1001)
 * */
enum class AppCode(val code: Int) {
    RUN(1000),
    MAINTAIN(1001);
}

/**
 * 運行資訊
 * @param versionName >> 版本名
 * @param url >> 下載連結
 * @param content >> 更新內容
 * @param size >> app大小
 * @param isCompulsory >> 是否強制更新
 * */
data class RunInfo(
    @SerializedName("version_name") val versionName: String,
    @SerializedName("google_play_url") val url: String,
    @SerializedName("content") val content: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("is_compulsory") val isCompulsory: Boolean = true
)

/**
 * 維護資訊
 * @param content >> 維護內容
 * @param time >> 預計完成時間
 * */
data class MaintainInfo(
    @SerializedName("content") val content: String? = null,
    @SerializedName("time") val time: Date? = null
)
