package edu.yujie.socketex.finish.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.PublishRelay
import timber.log.Timber

class ProcessLifeObs(private val owner: LifecycleOwner) : DefaultLifecycleObserver {

    val appForegroundRelay = PublishRelay.create<Boolean>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Timber.d("onStart()")
        appForegroundRelay.accept(true)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.d("onStop()")
        appForegroundRelay.accept(false)
    }
}