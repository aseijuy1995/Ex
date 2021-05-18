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

data class UpdateInfo(
    val versionName: String,//版本名
    val url: String,//google play url
    val desc: String,//description
    val size: String,//apk size
    val isMandatory: Boolean//是否強制更新
)