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
class AuthRequestInterceptor(val cxt: Context, val tokenCallback: () -> TokenInfo) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val userPref = runBlocking { cxt.userPref.data.first() }

        val currentTime = System.currentTimeMillis()
        if (currentTime > userPref.expiresTime) {
            dealWithExpireToken(
                cxt = cxt,
                tokenCallback = tokenCallback
            )
        }

        val token = when (userPref.tokenType) {
            UserPref.TokenType.Unknown -> ""
            UserPref.TokenType.Basic -> "Basic ${userPref.dealWithBasicToken(cxt = cxt)}"
            UserPref.TokenType.Bearer -> "Bearer ${userPref.accessToken}"
            else -> ""
        }

        return chain.proceed(chain.request().newBuilder().apply {
            if (token.isNotEmpty()) addHeader("Authorization", token)
        }.build())
    }
}

/**
 * Auth驗證器：code >> 401
 * */
class AuthResponseInterceptor(val cxt: Context, val tokenCallback: () -> TokenInfo) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val userPref = runBlocking { cxt.userPref.data.first() }

        dealWithExpireToken(
            cxt = cxt,
            tokenCallback = tokenCallback
        )

        val token = when (userPref.tokenType) {
            UserPref.TokenType.Unknown -> ""
            UserPref.TokenType.Basic -> "Basic ${userPref.dealWithBasicToken(cxt = cxt)}"
            UserPref.TokenType.Bearer -> "Bearer ${userPref.accessToken}"
            else -> ""
        }

        return response.request.newBuilder()
            .apply {
                if (token.isNotEmpty()) addHeader("Authorization", token)
            }
            .build()
    }
}

/**
 * refreshToken驗證器：code >> 410
 * */
class ReAuthResponseInterceptor(val refreshTokenInvalidCallback: () -> Unit) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(request = chain.request())
        when (response.code) {
            410 -> refreshTokenInvalidCallback.invoke()
        }
        return response
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
            cxt.userPref.setPassword("")
            cxt.userPref.setAccessToken(token)
        }
        token
    }
}

/**
 * 呼叫refreshToken Api & 處理UserPref狀態
 * */
private fun dealWithExpireToken(cxt: Context, tokenCallback: () -> TokenInfo) {
    val refreshTokenRsp = tokenCallback.invoke()

    runBlocking {
        if (refreshTokenRsp.accessToken.isNotEmpty())
            cxt.userPref.setAccessToken(refreshTokenRsp.accessToken)
        if (refreshTokenRsp.refreshToken.isNotEmpty())
            cxt.userPref.setRefreshToken(refreshTokenRsp.refreshToken)
    }
}
