package edu.yujie.lifecyclesex.process

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class MyProcessObserver(lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    private val TAG = javaClass.simpleName

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("$TAG:onStart()")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("$TAG:onStop()")
    }
}