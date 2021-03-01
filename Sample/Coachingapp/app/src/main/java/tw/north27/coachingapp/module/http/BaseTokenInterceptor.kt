package tw.north27.coachingapp.module.http

import android.content.Context
import android.util.Base64
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import tw.north27.coachingapp.module.pref.SignInModule

class BaseTokenInterceptor(val context: Context) : Interceptor {

    private val signInModule = SignInModule(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val signInDataStore = runBlocking { signInModule.getValue { it }.first() }
        val accessToken = signInDataStore.accessToken?.let { "Basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP) } ?: ""

        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", accessToken)
                .build()
        )
    }
}