package com.example.websockerext.const

import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://www.letsgoshopping.com.tw/"
inline fun <reified T> createRetrofit(
    okHttpClient: OkHttpClient,
    baseUrl: String,
    factory: CallAdapter.Factory
): T = Retrofit.Builder().apply {
    baseUrl(baseUrl)
    addConverterFactory(GsonConverterFactory.create())
    addCallAdapterFactory(factory)
    client(okHttpClient)
}.build().create(T::class.java)

fun createOkHttp(): OkHttpClient =
    OkHttpClient.Builder().apply {
        connectTimeout(10, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(10, TimeUnit.SECONDS)
        callTimeout(10, TimeUnit.SECONDS)
    }.build()