package tw.north27.coachingapp.ext

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

fun Context.startProcessLifeObs() = ProcessLifeObs(this)

class ProcessLifeObs(private val cxt: Context) : DefaultLifecycleObserver, CoroutineScope {

    private val dataStore = cxt.createDataStorePref()

    companion object {
        /**
         * true - foreground
         * false - background
         * */
        val IS_APP_ON_FOREGROUND = "IS_APP_ON_FOREGROUND"
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        dataStore.setBoolean(IS_APP_ON_FOREGROUND, true, this)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        dataStore.setBoolean(IS_APP_ON_FOREGROUND, false, this)
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}