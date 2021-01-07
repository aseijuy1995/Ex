package edu.yujie.socketex

import android.util.Log
import com.orhanobut.logger.Logger
import timber.log.Timber

object LogTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG || Log.isLoggable("MyTag", Log.DEBUG)) {
            Logger.log(priority, tag, message, t)
        }
    }
}