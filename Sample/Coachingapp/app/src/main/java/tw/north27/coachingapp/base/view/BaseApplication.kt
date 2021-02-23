package tw.north27.coachingapp.base.view

import android.app.Application
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.const.modelModules
import tw.north27.coachingapp.const.repoModules
import tw.north27.coachingapp.const.utilModules
import tw.north27.coachingapp.const.viewModules
import tw.north27.coachingapp.ext.startKoinModules
import tw.north27.coachingapp.ext.startProcessLifeObs
import tw.north27.coachingapp.ext.startStrictMode
import tw.north27.coachingapp.ext.startTimberLogger

class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: PublishRelay<Boolean>
    }

    override fun onCreate() {
        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        if (BuildConfig.DEBUG) startTimberLogger()

        startProcessLifeObs().also { appForegroundRelay = it.appForegroundRelay }

        startKoinModules(utilModules, modelModules, repoModules, viewModules)

    }

}