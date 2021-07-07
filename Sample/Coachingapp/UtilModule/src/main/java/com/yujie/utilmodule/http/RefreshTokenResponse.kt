package com.yujie.utilmodule.http

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String = ""
)