package com.yujie.utilmodule.http

import okhttp3.Interceptor

/**
 * OkHttp設定
 * @param connectSec >> 連接超時秒數(s)
 * @param readSec >> IO讀取超時秒數(s)
 * @param writeSec >> IO寫入超時秒數(s)
 * @param callSec >> 呼叫超時秒數(s)
 * @param isReConnect >> 失敗時重連(s)
 * @param logIntcp >> log攔截
 * @param authReqIntcp >> 身分請求攔截
 * @param authRspIntcp >> 身分響應攔截
// * 完整請求超時
 * */
data class OkHttpConfig(
		val connectSec: Long = 5,
		val readSec: Long = 30,
		val writeSec: Long = 30,
		val callSec: Long = 30,
		val isReConnect: Boolean = true,
		val logIntcp: Interceptor = logInterceptor,
		val authReqIntcp: Interceptor? = null,
		val authRspIntcp: Interceptor? = null,
		//
//		val refTokenRspIntcp: Interceptor? = null
)