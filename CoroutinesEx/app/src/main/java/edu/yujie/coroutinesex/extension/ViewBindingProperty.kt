package edu.yujie.databindingext.extension

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author YuJie on 2020/10/17
 * @describe ViewBinding - 主要消除Fragment中使用ViewBinding的Template
 * @param 參數
 */
abstract class ViewBindingProperty<R, T>(val thisRef: R, val viewBinder: () -> T) :
    ReadOnlyProperty<R, T>, DefaultLifecycleObserver {
    private var mBinding: T? = null
    private val mHandler = Handler(Looper.getMainLooper())

    abstract val lifecycleOwner: LifecycleOwner

    @MainThread
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        val lifecycle = lifecycleOwner.lifecycle
        if (lifecycle.currentState != Lifecycle.State.DESTROYED)
            lifecycle.addObserver(this)
        else
            mHandler.post { mBinding = null }
        return mBinding ?: viewBinder().also {
            mBinding = it
        }
    }

    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        lifecycleOwner.lifecycle.removeObserver(this)
        mHandler.post { mBinding = null }
    }

}