package com.yujie.utilmodule.base

import android.app.Application
import com.yujie.utilmodule.util.startLog
import startAppState

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startAppState()
        startLog()
    }
}