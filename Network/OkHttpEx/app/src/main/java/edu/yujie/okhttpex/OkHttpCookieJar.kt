package edu.yujie.okhttpex

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

object OkHttpCookieJar : CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        TODO("Not yet implemented")
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        TODO("Not yet implemented")
    }
}