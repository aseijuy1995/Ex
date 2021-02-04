package edu.yujie.socketex.finish.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jakewharton.rxrelay3.PublishRelay

class ProcessLifeObs(private val owner: LifecycleOwner) : DefaultLifecycleObserver {

    private val TAG = javaClass.simpleName

    val appForegroundRelay = PublishRelay.create<Boolean>()

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        appForegroundRelay.accept(true)
        println("$TAG onStart()")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        appForegroundRelay.accept(false)
        println("$TAG onStop()")
    }
}