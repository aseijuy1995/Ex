package tw.north27.coachingapp.module.http

import android.content.Context
import android.util.Base64
import com.yujie.prefmodule.dataStore.getAccessToken
import com.yujie.prefmodule.dataStore.userPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class BaseTokenInterceptor(val cxt: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val accessToken = runBlocking { cxt.userPref.getAccessToken().first() }

        val accessToken2 = accessToken?.let { "Basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP) } ?: ""

        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", accessToken2)
                .build()
        )
    }
}