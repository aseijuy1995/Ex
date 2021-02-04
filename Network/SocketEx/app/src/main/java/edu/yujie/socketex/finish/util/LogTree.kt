package edu.yujie.socketex.finish.util

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import edu.yujie.socketex.BuildConfig
import timber.log.Timber

object LogTree : Timber.DebugTree() {

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.DEBUG) Logger.log(priority, tag, message, t)
    }
}