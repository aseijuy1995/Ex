package tw.north27.coachingapp.ext

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.core.module.Module

fun Application.startKoinModules(vararg modules: Module) {
    org.koin.core.context.startKoin {
        androidContext(this@startKoinModules.applicationContext)
        androidLogger(Level.ERROR)
        modules(*modules)
    }
}