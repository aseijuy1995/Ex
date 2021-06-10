package tw.north27.coachingapp.chat

import android.content.Context
import androidx.startup.Initializer
import androidx.work.WorkManager

/**
 * window: gradlew :app:lintDebug
 * */

class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}