package com.yujie.core_lib.base

import android.app.Application
import com.yujie.core_lib.util.startLog
import startAppState

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startAppState()
        startLog()
    }
}