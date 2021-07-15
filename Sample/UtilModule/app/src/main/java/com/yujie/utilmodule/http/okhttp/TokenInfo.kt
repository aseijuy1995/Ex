package com.yujie.utilmodule.http.okhttp

import com.google.gson.annotations.SerializedName
import com.yujie.utilmodule.UserPref

/**
 * Token資訊
 * @param tokenType >> token類型
 * @param accessToken >> 訪問用token
 * @param refreshToken >> 刷新用token
 * @param expiresIn >> 過期日期在幾秒後(s)
 * */
data class TokenInfo(
    @SerializedName("token_type") open val tokenType: UserPref.TokenType,
    @SerializedName("access_token") open val accessToken: String,
    @SerializedName("refresh_token") open val refreshToken: String,
    @SerializedName("expires_in") open val expiresIn: Long,
)