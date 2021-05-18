package tw.north27.coachingapp.model.result

data class AppConfig(
    val appState: AppState,
    val maintainInfo: MaintainInfo? = null,
    val updateInfo: UpdateInfo? = null
)

/**
 * MAINTAIN >> 維護中
 * RUN >> 運行中
 * */
enum class AppState {
    MAINTAIN, RUN;
}

/**
 * title >> 標題
 * text >> 內文
 * time >> 預計完成時間
 * */
data class MaintainInfo(
    val title: String,
    val text: String,
    val time: String
)

/**
 * versionName >> 版本名
 * url >> google play url
 * text >> 內文
 * size >> apk size
 * isMandatory >> 是否強制更新
 * */
data class UpdateInfo(
    val versionName: String,
    val url: String,
    val text: String,
    val size: String,
    val isMandatory: Boolean
)