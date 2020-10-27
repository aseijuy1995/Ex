package edu.yujie.lifecyclesex.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class MyDefaultObserver(lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    private val TAG = javaClass.simpleName

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        println("$TAG:onCreate()")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        println("$TAG:onStart()")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        println("$TAG:onResume()")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        println("$TAG:onPause()")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        println("$TAG:onStop()")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("$TAG:onDestroy()")
    }


}