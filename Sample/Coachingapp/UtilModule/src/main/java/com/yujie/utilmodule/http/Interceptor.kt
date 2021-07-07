package com.yujie.utilmodule.http

import android.content.Context
import com.yujie.utilmodule.UserPref
import com.yujie.utilmodule.pref.setAccessToken
import com.yujie.utilmodule.pref.setPassword
import com.yujie.utilmodule.pref.setRefreshToken
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.logI
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Log攔截器
 * */
var logInterceptor = HttpLoggingInterceptor { message -> logI(message) }.apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

/**
 * Auth攔截器
 * */
class AuthRequestInterceptor(val cxt: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val userPref = runBlocking { cxt.userPref.data.first() }

        val token = when (userPref.tokenType) {
            UserPref.TokenType.UNKNOWN -> ""
            UserPref.TokenType.BASIC -> userPref.dealWithBasicToken()
            UserPref.TokenType.BEARER -> "Bearer ${userPref.accessToken}"
            else -> ""
        }

        if (token.isNotEmpty()) builder.addHeader("Authorization", token)

        return chain.proceed(builder.build())
    }

    /**
     * 清空存取的password & 設置basic token
     * */
    private fun UserPref.dealWithBasicToken(): String {
        return if (accessToken.isNotEmpty()) {
            accessToken
        } else {
            val token = Credentials.basic(account, password)
            runBlocking {
                cxt.userPref.setPassword("")
                cxt.userPref.setAccessToken(token)
            }
            token
        }
    }

}


/**
 * Auth驗證器
 * */
class AuthResponseInterceptor(
    val cxt: Context,
    val refreshTokenCallback: () -> TokenResponse
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val tokenResponse = refreshTokenCallback.invoke()

        runBlocking {
            if (tokenResponse.accessToken.isNotEmpty())
                cxt.userPref.setAccessToken(tokenResponse.accessToken)
            if (tokenResponse.refreshToken.isNotEmpty())
                cxt.userPref.setRefreshToken(tokenResponse.refreshToken)
        }

        return response.request.newBuilder()
            .header("Authorization", tokenResponse.accessToken)
            .build()
    }

}

/**
 * refreshToken驗證器：410
 * */
class ReAuthResponseInterceptor(
    val refreshTokenCallback: () -> Unit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(request = chain.request())
        when (response.code) {
            410 -> {
                refreshTokenCallback.invoke()
            }
        }
        return response
    }
}