package edu.yujie.lifecyclesex

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MyObserver(lifecycleOwner: LifecycleOwner) : LifecycleObserver {
    private val TAG = javaClass.simpleName

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() = println("$TAG:create()")

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() = println("$TAG:start()")

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() = println("$TAG:resume()")

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = println("$TAG:pause()")

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = println("$TAG:stop()")

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = println("$TAG:destroy()")

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun any() = println("$TAG:any()")
}