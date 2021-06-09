package com.yujie.utilmodule.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import timber.log.Timber

/**
 * 啟用Timber & Logger
 * */
fun startLog() = Timber.plant(object : Timber.DebugTree() {

		init {
				Logger.addLogAdapter(AndroidLogAdapter())
		}

		override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
				Logger.log(priority, tag, message, t)
		}
})

fun logD(msg: String) = Timber.d(msg)

fun logD(t: Throwable) = Timber.d(t)

fun logI(msg: String) = Timber.i(msg)

fun logI(t: Throwable) = Timber.i(t)

fun logW(msg: String) = Timber.w(msg)

fun logW(t: Throwable) = Timber.w(t)

fun logE(msg: String) = Timber.e(msg)

fun logE(t: Throwable) = Timber.e(t)

fun logV(msg: String) = Timber.v(msg)

fun logV(t: Throwable) = Timber.v(t)

fun logWTF(msg: String) = Timber.wtf(msg)

fun logWTF(t: Throwable) = Timber.wtf(t)