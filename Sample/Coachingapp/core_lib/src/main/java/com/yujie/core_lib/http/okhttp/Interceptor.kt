package com.yujie.core_lib.http.okhttp

import android.content.Context
import com.yujie.core_lib.UserPref
import com.yujie.core_lib.pref.*
import com.yujie.core_lib.util.logI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Log攔截器
 * */
var logInterceptor = HttpLoggingInterceptor { message -> logI(message) }.apply {
		level = HttpLoggingInterceptor.Level.BODY
}

/**
 * Basic Auth攔截器
 * */
class BasicAuthRequestInterceptor(val cxt: Context) : Interceptor {
		override fun intercept(chain: Interceptor.Chain): Response {
				val userPref = runBlocking { cxt.userPref.data.first() }

				val token = if (userPref.tokenType == UserPref.TokenType.Basic) userPref.dealWithBasicToken(cxt = cxt) else ""

				val request = chain.request().newBuilder()
						.apply {
								if (token.isNotEmpty()) addHeader("Authorization", "${UserPref.TokenType.Basic} $token")
						}.build()

				return chain.proceed(request)
		}
}

/**
 * Bearer Auth攔截器
 * @param expiredInCallback >> 處理accessToken過期
 * */
class BearerAuthRequestInterceptor(val cxt: Context, val expiredInCallback: () -> TokenInfo) : Interceptor {
		override fun intercept(chain: Interceptor.Chain): Response {
				val userPref = runBlocking { cxt.userPref.data.first() }

				val currentTime = System.currentTimeMillis() / 1000
				if (currentTime > userPref.expiresTime) {
						dealWithExpiredToken(
								cxt = cxt,
								tokenCallback = expiredInCallback
						)
				}

				val token = if (userPref.tokenType == UserPref.TokenType.Bearer) userPref.accessToken else ""

				val request = chain.request().newBuilder().apply {
						if (token.isNotEmpty()) addHeader("Authorization", "${UserPref.TokenType.Bearer} $token")
				}.build()

				return chain.proceed(request)
		}
}

/**
 * Auth驗證器
 * @param callback401 >> 處理accessToken過期
 * @param callback410 >> 處理refreshToken過期
 * */
class AuthResponseInterceptor(
		val cxt: Context,
		val callback401: () -> TokenInfo,
		val callback410: () -> Unit,
) : Interceptor {

		override fun intercept(chain: Interceptor.Chain): Response {
				val response = chain.proceed(chain.request())

				val userPref = runBlocking { cxt.userPref.data.first() }

				when (response.code) {
						401 -> {
								dealWithExpiredToken(
										cxt = cxt,
										tokenCallback = callback401
								)
						}

						410 -> {
								callback410.invoke()
						}
				}

				val token =
						if (userPref.tokenType == UserPref.TokenType.Bearer)
								userPref.accessToken
						else ""

				val request = chain.request().newBuilder().apply {
						if (token.isNotEmpty()) addHeader("Authorization", "${UserPref.TokenType.Bearer} $token")
				}.build()

				return chain.proceed(request)
		}
}

/**
 * 取得accessToken & 處理UserPref狀態
 * */
private fun UserPref.dealWithBasicToken(cxt: Context): String {
		return if (accessToken.isNotEmpty()) {
				accessToken
		} else {
				val token = Credentials.basic(account, password).replaceFirst(oldValue = "Basic", newValue = "").trim()
				runBlocking {
						cxt.userPref.apply {
								setAccount("")
								setPassword("")
								setAccessToken(token)
						}
				}
				token
		}
}

/**
 * 呼叫refreshToken Api & 處理UserPref狀態
 * */
private fun dealWithExpiredToken(cxt: Context, tokenCallback: () -> TokenInfo) {
		val refreshTokenRsp = tokenCallback.invoke()
		runBlocking {
				cxt.userPref.apply {
						setTokenType(refreshTokenRsp.tokenType)
						setAccessToken(refreshTokenRsp.accessToken)
						setRefreshToken(refreshTokenRsp.refreshToken)
						setExpiresTime((System.currentTimeMillis() / 1000) + refreshTokenRsp.expiresIn)
				}
		}
}
