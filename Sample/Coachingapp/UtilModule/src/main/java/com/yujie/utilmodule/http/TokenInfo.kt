package com.yujie.utilmodule.http

import com.google.gson.annotations.SerializedName

/**
 * Token資訊
 * @param tokenType >> token類型
 * @param accessToken >> 訪問用token
 * @param refreshToken >> 刷新用token
 * @param expiresIn >> 過期日期在幾秒後(s)
 * */
data class TokenInfo(
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
)