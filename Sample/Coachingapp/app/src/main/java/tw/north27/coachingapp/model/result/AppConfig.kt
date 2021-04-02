package tw.north27.coachingapp.model.result

data class AppConfig(
    val appState: AppState,
    val maintainInfo: MaintainInfo? = null,
    val updateInfo: UpdateInfo? = null
)

enum class AppState {
    RUN,//運行中
    MAINTAIN;//維護中
}

data class MaintainInfo(
    val desc: String,//description
    val time: String//預計完成時間
)

data class UpdateInfo(
    val versionName: String,//版本名
    val url: String,//google play url
    val desc: String,//description
    val size: String,//apk size
    val isMandatory: Boolean//是否強制更新
)