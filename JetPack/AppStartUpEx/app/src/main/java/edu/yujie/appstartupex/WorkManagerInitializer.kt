package edu.yujie.appstartupex

import android.content.Context
import androidx.startup.Initializer
import androidx.work.WorkManager

/**
 * @author YuJie on 2020/10/17
 * @describe Run lint checks:The App Startup library includes a set of lint rules that you can use to check whether you've defined your component initializers correctly. You can perform these lint checks by running ./gradlew :app:lintDebug from the command line.
 */

// Initializes WorkManager.
class WorkManagerInitializer : Initializer<WorkManager> {
    private val TAG = javaClass.simpleName
    override fun create(context: Context): WorkManager {
        println("$TAG:create()")
        return WorkManager.getInstance(context)
    }

    // No dependencies on other libraries.
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}