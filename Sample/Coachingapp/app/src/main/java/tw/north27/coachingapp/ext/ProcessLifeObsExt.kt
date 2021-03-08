package tw.north27.coachingapp.ext

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay

fun Application.startProcessLifeObs() = ProcessLifeObs(ProcessLifecycleOwner.get())

class ProcessLifeObs(private val owner: LifecycleOwner) : DefaultLifecycleObserver {

    val appForegroundRelay = BehaviorRelay.create<Boolean>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        appForegroundRelay.accept(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        appForegroundRelay.accept(false)
    }
}