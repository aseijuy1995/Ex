package edu.yujie.hiltex.arch

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import edu.yujie.hiltex.HiltSimple
import javax.inject.Inject

@HiltAndroidApp
class App : Application()
//    , Configuration.Provider
{
    @Inject
    lateinit var hiltSimple: HiltSimple

//    @Inject
//    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        val msg = hiltSimple.doSomething()
        println(msg)
    }

//    override fun getWorkManagerConfiguration(): Configuration =
//        Configuration.Builder().setWorkerFactory(workerFactory).build()
}