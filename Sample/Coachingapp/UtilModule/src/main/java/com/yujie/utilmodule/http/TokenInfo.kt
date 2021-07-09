package com.yujie.utilmodule.http

import com.google.gson.annotations.SerializedName

/**
 * @param accessToken >> 訪問用token
 * @param refreshToken >> 刷新用token
 * @param tokenType >> token類型
 * @param expiresIn >> 過期日期在幾秒後
 * */
data class TokenInfo(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int,
)