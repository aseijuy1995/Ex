package tw.north27.coachingapp.module.http

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext.safeApiSimpleResults
import tw.north27.coachingapp.module.pref.UserModule
import kotlin.coroutines.CoroutineContext

class BaseTokenExpiredInterceptor(
    private val context: Context,
) : Interceptor, CoroutineScope {

    private val signInModule = UserModule(context)

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        when (response.code) {
            //access token
            401 -> {
                val service = RetrofitManager.get(BuildConfig.BASE_URL, OkHttpUtil(context, null, null).client).create<IApiService>()
                val refreshToken = runBlocking { signInModule.getValue { it.refreshToken }.first() }

                launch {
                    val results = safeApiSimpleResults { service.refreshToken(refreshToken) }

                    when (results) {
                        is SimpleResults.Successful -> {
                            results.data.also {
                                signInModule.setValue(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                            //retry
                        }
                        //refresh token
                        is SimpleResults.ClientErrors -> {
                            //go login page
                        }
                        is SimpleResults.NetWorkError -> {
                        }
                    }
                }
            }
        }
        return response
    }
}