package tw.north27.coachingapp.model.result

data class VersionResult(
    val version: String,//版本號
    val apkDownloadUrl: String,//下載url
    val updateLog: String,//更新訊息
    val targetSize: String,//apk size
    val newMd5: String,//md5
    val isMandatoryUpdate: Boolean//是否強制更新
)