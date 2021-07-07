package com.yujie.utilmodule.http

data class RetrofitConfig(
    val baseUrl: String,
    val entity: OkHttpUtil.Entity,
    val converterFactory: ConverterFactory,
    val callAdapterFactory: CallAdapterFactory? = null
)