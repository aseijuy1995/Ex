package com.yujie.utilmodule.http

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String = ""
)