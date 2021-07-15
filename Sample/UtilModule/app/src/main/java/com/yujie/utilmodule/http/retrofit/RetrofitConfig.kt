package com.yujie.utilmodule.http.retrofit

import com.yujie.utilmodule.http.CallAdapterFactory
import com.yujie.utilmodule.http.ConverterFactory
import com.yujie.utilmodule.http.okhttp.OkHttpManager

/**
 * Retrofit設定
 * @param baseUrl >> 連嫌url
 * @param entity >> IO讀取超時秒數(s)
 * @param converterFactory >> 轉換器
 * @param callAdapterFactory >> 調用適配器
 * */
data class RetrofitConfig(
		val baseUrl: String,
		val entity: OkHttpManager.Entity,
		val converterFactory: ConverterFactory,
		val callAdapterFactory: CallAdapterFactory? = null
)