package com.yujie.utilmodule.http

import com.yujie.utilmodule.http.manager.OkHttpManager

data class RetrofitConfig(
		val baseUrl: String,
		val entity: OkHttpManager.Entity,
		val converterFactory: ConverterFactory,
		val callAdapterFactory: CallAdapterFactory? = null
)