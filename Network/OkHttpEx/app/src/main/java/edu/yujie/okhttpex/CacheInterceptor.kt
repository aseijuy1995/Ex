package edu.yujie.okhttpex

import okhttp3.Interceptor
import okhttp3.Response

object CacheInterceptor : Interceptor {
    private val cacheControl = "Cache-Control"
    private val pragma = "Pragma"
    private val maxAge = "max-age"
    private val cacheTime = 60 * 2//seconds

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val newResponse = response.newBuilder()
            .removeHeader(cacheControl)
            .removeHeader(pragma)
            .header(cacheControl, "$maxAge=$cacheTime")
            .build()
        return newResponse
    }
}