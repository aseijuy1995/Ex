package edu.yujie.lifecyclesex.service

import androidx.lifecycle.*

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class MyServiceObserver(lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    private val TAG = javaClass.simpleName

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        println("$TAG:onCreate()")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        println("$TAG:onDestroy()")
    }
}