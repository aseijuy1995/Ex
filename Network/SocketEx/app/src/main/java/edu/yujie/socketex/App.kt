package edu.yujie.socketex

import android.app.Application
import edu.yujie.socketex.util.OkHttpUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {
    private val module = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }

        viewModel { SocketViewModel() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(module)
        }
//        val request = OneTimeWorkRequestBuilder<ChatWorker>().build()
//        WorkManager.getInstance(this).enqueue(request)


    }
}