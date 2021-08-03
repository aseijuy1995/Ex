package tw.north27.coachingapp.model.response

import com.google.gson.annotations.SerializedName
import com.yujie.core_lib.util.UpdateApp
import java.util.*

/**
 * 請求獲取App初始設定
 * @param deviceType >> 設備類型
 * */
data class AppConfigRequest(
    @SerializedName("device_type") val deviceType: String
)

/**
 * 設備類型
 * @param ANDROID >> Android
 * @param IOS >> ios
 * */
enum class DeviceType(val type: String) {
    ANDROID("android"),
    IOS("ios");

    override fun toString(): String = type
}

/**
 * App初始設定
 * @param appCode >> App狀態碼 運行(2000)、阻擋(2001)
 * @param motionInfo >> 運行資訊
 * @param defendInfo >> 阻擋資訊
 * */
data class AppConfig(
    @SerializedName("code") val appCode: Int,
    @SerializedName("motion_info") val motionInfo: MotionInfo? = null,
    @SerializedName("defend_info") val defendInfo: DefendInfo? = null,
)

/**
 * App狀態碼
 * @param MOTION >> 運行(2000)
 * @param DEFEND >> 阻擋(2001)
 * */
enum class AppCode(val code: Int) {
    MOTION(2000),
    DEFEND(2001);

    override fun toString(): String = code.toString()
}

/**
 * 運行資訊
 * @param bgUrl >> 運行背景圖
 * @param title >> 運行標題
 * @param versionNameMode >> 版本名模式
 * @param versionName >> 版本名
 * @param url >> 下載連結
 * @param content >> 更新內容
 * @param size >> app大小
 * @param isCompulsory >> 是否強制更新
 * */
data class MotionInfo(
    @SerializedName("bg_url") val bgUrl: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("version_name_mode") val versionNameMode: UpdateApp.VersionNameMode = UpdateApp.VersionNameMode.DEFAULT,
    @SerializedName("version_name") val versionName: String,
    @SerializedName("url") val url: String,
    @SerializedName("content") val content: String = "",
    @SerializedName("size") val size: String = "",
    @SerializedName("is_compulsory") val isCompulsory: Boolean = true
)

/**
 * 阻擋資訊
 * @param bgUrl >> 阻擋背景圖
 * @param title >> 阻擋標題
 * @param content >> 阻擋內容
 * @param time >> 預計完成時間
 * */
data class DefendInfo(
    @SerializedName("bg_url") val bgUrl: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("time") val time: Date? = null
)
