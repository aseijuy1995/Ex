package edu.yujie.socketex.finish.repo

import edu.yujie.socketex.UserBean
import edu.yujie.socketex.finish.bean.InitBean
import edu.yujie.socketex.finish.inter.IApiRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ApiRepo() : IApiRepo {
//    private val service: IApiService

    override suspend fun getInit(): InitBean = withContext(Dispatchers.IO) {
        delay(2000L)
        InitBean(
            version = "1.0.0",
            apkFileUrl = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
            updateLog = "1. 工程師持續加班(24h)\n2. 努力除掉了Dug蟲蟲\n3. 泡杯熱茶休息一下~~~",
            targetSize = "5M",
            newMd5 = "A818AD325EACC199BC62C552A32C35F2",
            isMandatoryUpdate = false
        )
    }

    override suspend fun postCheckSignIn(account: String, authToken: String, deviceToken: String): UserBean = withContext(Dispatchers.IO) {
        delay(2000L)
        UserBean.newBuilder().apply {
            this.account = account
            this.authToken = authToken
            this.deviceToken = deviceToken
            this.name = "Owner"
            this.headerUrl = "https://obs.line-scdn.net/0h7IGZqu6raGZ6I0JI5ksXMUB1awlJT3tlHhU5ZTlNNlIFFic4RkdwU1YiM1dfRi84FE0jBlwrc1dfGitjT0Zw/w644"
        }.build()
    }


}