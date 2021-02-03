package edu.yujie.socketex.repo

import edu.yujie.socketex.bean.InitSettingBean
import edu.yujie.socketex.inter.IApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ApiRepo() : IApiRepo {
//    private val service: IApiService

    override suspend fun getInit(): InitSettingBean = withContext(Dispatchers.IO) {
        delay(1500)
        InitSettingBean(
            isUpdate = true,
            newVersion = "1.0.1",
            apkFileUrl = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
            updateLog = "1.工程師加班24h\n2.除掉了一些bug蟲蟲\n3.準備放假樓~",
            targetSize = "5M",
            newMd5 = "A818AD325EACC199BC62C552A32C35F2",
            isMandatoryUpdate = false
        )
    }
    //    = service.getInit()


}