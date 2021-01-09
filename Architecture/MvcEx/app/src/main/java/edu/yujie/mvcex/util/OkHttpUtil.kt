package edu.yujie.mvcex.util

import android.content.Context
import edu.yujie.mvcex.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpUtil private constructor(private val context: Context) {
    private val TAG = javaClass.simpleName
    val client: OkHttpClient

    companion object : SingletonProperty<OkHttpUtil, Context>(::OkHttpUtil)

    val loggerInterceptor = HttpLoggingInterceptor().apply {
        level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            false -> HttpLoggingInterceptor.Level.NONE
        }
    }

    init {
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)//連接超時
            .readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
            .writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
            .callTimeout(60, TimeUnit.SECONDS)//完整請求超時
            .retryOnConnectionFailure(true)//失敗重連
            .addInterceptor(loggerInterceptor)
            .build()
    }

}