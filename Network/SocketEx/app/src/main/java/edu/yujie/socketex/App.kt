package edu.yujie.socketex

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import edu.yujie.retrofitex.OkHttpUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {
    private val module = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(module)
        }
        val request = OneTimeWorkRequestBuilder<ChatWorker>().build()
        WorkManager.getInstance(this).enqueue(request)


    }
}