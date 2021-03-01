package tw.north27.coachingapp.module.base.application

import android.app.Application
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.ext.startProcessLifeObs
import tw.north27.coachingapp.ext.startTimberLogger

open class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: PublishRelay<Boolean>
    }

    override fun onCreate() {
//        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        if (BuildConfig.DEBUG) startTimberLogger()

        startProcessLifeObs().also { appForegroundRelay = it.appForegroundRelay }
    }

}