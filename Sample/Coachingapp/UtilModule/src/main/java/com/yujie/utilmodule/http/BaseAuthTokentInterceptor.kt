import android.content.Context
import android.util.Base64
import com.yujie.utilmodule.pref.getAccessToken
import com.yujie.utilmodule.pref.getDelegate
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.coroutines.CoroutineContext

enum class HttpAuth {
		NONE, BASIC, BEARER
}

class BaseAuthTokenRequestInterceptor(
		private val cxt: Context,
		private val httpAuth: HttpAuth = HttpAuth.NONE
) : Interceptor {

		override fun intercept(chain: Interceptor.Chain): Response {
				val request = chain.request()
				//
				var authToken: String? = null
				when (httpAuth) {
						HttpAuth.NONE -> {
								return chain.proceed(request)
						}

						HttpAuth.BASIC -> {
								val userPref = runBlocking { cxt.userPref.getDelegate { it }.first() }
								val account = userPref.account
								val password = userPref.password
								authToken = if (account.isNotEmpty() && password.isNotEmpty()) "Basic ${Credentials.basic(account, password)}" else ""
						}

						HttpAuth.BEARER -> {
								val accessToken = runBlocking { cxt.userPref.getAccessToken().first() }
								authToken = if (accessToken.isNotEmpty()) "Bearer ${Base64.encodeToString(accessToken.toByteArray(), Base64.NO_WRAP)}" else ""
						}
				}
				val newRequest = request.newBuilder()
						.addHeader("Authorization", authToken)
						.build()

				return chain.proceed(newRequest)
		}
}

class BaseAuthTokenResponseInterceptor(
		private val cxt: Context,
		private val httpAuth: HttpAuth = HttpAuth.NONE
) : Interceptor, CoroutineScope {

		override val coroutineContext: CoroutineContext
				get() = Job()

		override fun intercept(chain: Interceptor.Chain): Response {
				val response = chain.proceed(chain.request())
//				when (response.code) {
//						//access token
//						401 -> {
//								val service = RetrofitManager.get(BuildConfig.BASE_URL, OkHttpUtil(cxt, null, null).okHttpClient).create<IApiService>()
//								val refreshToken = runBlocking { cxt.userPref.getRefreshToken().first() }
//
//
//								launch {
//										val results = safeApiSimpleResults { service.refreshToken(refreshToken) }
//
//										when (results) {
//												is SimpleResults.Successful -> {
//														results.data.also {
//																cxt.userPref.setUserPref(
//																		accessToken = it.accessToken,
//																		refreshToken = it.refreshToken
//																)
//														}
//														//retry
//												}
//												//refresh token
//												is SimpleResults.ClientErrors -> {
//														//go login page
//												}
//
//												is SimpleResults.NetWorkError -> {
//												}
//										}
//								}
//						}
//				}
				return response
		}
}