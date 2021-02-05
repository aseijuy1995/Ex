package edu.yujie.socketex.const

import edu.yujie.socketex.SignInStorage
import edu.yujie.socketex.finish.result.AuthType
import edu.yujie.socketex.finish.result.InitResult
import edu.yujie.socketex.finish.result.SignInResultInfo
import kotlinx.coroutines.delay

class ApiService : IApiService {

    override suspend fun getInit(): InitResult {
        delay(2000L)
        return InitResult(
            version = "1.0.1",
            apkFileUrl = "https://play.google.com/store/apps/details?id=ojisan.Droid&hl=zh_TW",
            updateLog = "1. 工程師持續加班(24h)\n2. 努力除掉了Dug蟲蟲\n3. 泡杯熱茶休息一下~~~",
            targetSize = "5M",
            newMd5 = "A818AD325EACC199BC62C552A32C35F2",
            isMandatoryUpdate = false
        )
    }

    override suspend fun postSignIn(signInStorage: SignInStorage): SignInResultInfo {
        delay(2000L)
        return SignInResultInfo(
            account = signInStorage.account,
            authToken = signInStorage.authToken,
            memberGuid = signInStorage.guid,
            authType = AuthType.MOBILE
        )
    }

}