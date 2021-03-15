package tw.north27.coachingapp.ext

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import timber.log.Timber

fun startTimberLogger() = Timber.plant(LogDebugTree)

object LogDebugTree : Timber.DebugTree() {

    init {
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Logger.log(priority, tag, message, t)
    }
}