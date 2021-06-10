package com.yujie.utilmodule.http

import com.yujie.utilmodule.util.logD
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Log攔截器
 * */
var httpLoggingInterceptor = HttpLoggingInterceptor(BaseHttpLogger).apply {
		level = HttpLoggingInterceptor.Level.BASIC
}

object BaseHttpLogger : HttpLoggingInterceptor.Logger {
		override fun log(message: String) {
				logD(message)
		}
}