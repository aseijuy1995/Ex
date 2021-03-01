package tw.north27.coachingapp.module.http

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

object BaseHttpLoggingInterceptor : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.d(message)
    }
}