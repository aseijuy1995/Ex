package edu.yujie.okhttpex.cookie

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

//cookieJar
//https://juejin.cn/post/6844904097385021453
//https://juejin.cn/post/6844903646711267336
//https://www.jianshu.com/p/1a222a9394ce

object CookieJarImpl : CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        TODO("Not yet implemented")
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        TODO("Not yet implemented")
    }
}