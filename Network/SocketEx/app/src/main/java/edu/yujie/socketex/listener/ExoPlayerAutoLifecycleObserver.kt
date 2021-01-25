package edu.yujie.socketex.listener

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.Player

class ExoPlayerAutoLifecycleObserver(private val owner: LifecycleOwner, private val player: Player) : LifecycleObserver {
    private val TAG = javaClass.simpleName

    init {
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        println("$TAG: resume")
        player.play()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        println("$TAG: pause")
        player.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        println("$TAG: destroy")
        player.release()
    }

}