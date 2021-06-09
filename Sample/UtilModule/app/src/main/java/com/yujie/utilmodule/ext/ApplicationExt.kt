package com.yujie.utilmodule.ext

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

/**
 * 啟用Koin
 * */
fun Application.startKoinModules(vararg modules: Module) {
		startKoin {
				androidContext(this@startKoinModules.applicationContext)
				androidLogger(Level.ERROR)
				modules(*modules)
		}
}