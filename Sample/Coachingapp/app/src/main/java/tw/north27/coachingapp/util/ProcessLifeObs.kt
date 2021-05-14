package tw.north27.coachingapp.util

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.yujie.prefmodule.dataStore.dataStore
import com.yujie.prefmodule.dataStore.setString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProcessLifeObs(private val cxt: Context) : DefaultLifecycleObserver, CoroutineScope {

    companion object {
        /**
         * true - foreground
         * false - background
         * */
        val APP_VIEW_STATE = "APP_VIEW_STATE"
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        launch(Dispatchers.IO) {
            cxt.dataStore.setString(APP_VIEW_STATE, AppViewState.FOREGROUND.toString())
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        launch(Dispatchers.IO) {
            cxt.dataStore.setString(APP_VIEW_STATE, AppViewState.BACKGROUND.toString())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}

