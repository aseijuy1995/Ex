package edu.yujie.coroutinesex

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class LoginRepository {

    suspend fun makeLoginRequest(jsonBody: String): String = withContext(Dispatchers.IO) {
        delay(3000L)
        "Success"
    }
}