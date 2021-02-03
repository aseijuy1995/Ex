package edu.yujie.socketex.bean


data class InitSettingBean(
    val isUpdate: Boolean,//是否更新
    val newVersion: String,//新版本
    val apkFileUrl: String,//下載url
    val updateLog: String,//更新訊息
    val targetSize: String,//apk size
    val newMd5: String,//md5
    val isMandatoryUpdate: Boolean//是否強制更新
)