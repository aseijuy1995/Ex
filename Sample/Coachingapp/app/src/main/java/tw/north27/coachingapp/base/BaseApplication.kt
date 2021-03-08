package tw.north27.coachingapp.base

import android.app.Application
import com.jakewharton.rxrelay3.BehaviorRelay
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.consts.modelModules
import tw.north27.coachingapp.consts.repoModules
import tw.north27.coachingapp.consts.utilModules
import tw.north27.coachingapp.consts.viewModelModules
import tw.north27.coachingapp.ext.startKoinModules
import tw.north27.coachingapp.ext.startProcessLifeObs
import tw.north27.coachingapp.ext.startTimberLogger

class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: BehaviorRelay<Boolean>
    }

    override fun onCreate() {
//        if (BuildConfig.DEBUG) startStrictMode()
        super.onCreate()
        if (BuildConfig.DEBUG) startTimberLogger()

        startProcessLifeObs().also { appForegroundRelay = it.appForegroundRelay }
        startKoinModules(utilModules, modelModules, repoModules, viewModelModules)
    }

}