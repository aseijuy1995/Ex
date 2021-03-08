package tw.north27.coachingapp.util

import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.rxrelay3.PublishRelay

class FirebaseManager private constructor() {

    val tokenRelay = PublishRelay.create<String>()

    companion object {

        @Volatile
        private var sInstance: FirebaseMessaging? = null

        fun get(): FirebaseManager {
            sInstance ?: FirebaseMessaging.getInstance().also { sInstance = it }
            return FirebaseManager()
        }
    }

    fun register(): Task<String> = sInstance!!.token


}

