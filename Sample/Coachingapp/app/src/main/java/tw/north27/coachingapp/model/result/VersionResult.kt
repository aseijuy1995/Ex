package tw.north27.coachingapp.model.result

data class VersionResult(
    val versionName: String,//版本名
    val apkDownloadUrl: String,//下載url
    val updateLog: String,//更新訊息
    val apkSize: String,//apk size
    val newMd5: String,//md5
    val isMandatoryUpdate: Boolean//是否強制更新
)