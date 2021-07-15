package tw.north27.coachingapp.model.request

import com.google.gson.annotations.SerializedName

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
 * @param WEB >> web
 * */
enum class DeviceType(val type: String) {
    ANDROID("android"),
    IOS("ios"),
    WEB("web");

    override fun toString(): String = type
}
