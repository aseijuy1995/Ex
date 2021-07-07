package com.yujie.utilmodule.http

import okhttp3.Authenticator
import okhttp3.Interceptor

/**
 * 連接超時
 * IO讀取超時
 * IO寫入超時
 * 完整請求超時
 * */
data class OkHttpConfig(
    val connectSec: Long = 10,
    val readSec: Long = 10,
    val writeSec: Long = 30,
    val callSec: Long = 60,
    val isReConnect: Boolean = true,
    val logIntcp: Interceptor = logInterceptor,
    val authReqIntcp: Interceptor? = null,
    val authRspIntcp: Authenticator? = null,
    val intcpList: List<Interceptor> = emptyList<Interceptor>()
)