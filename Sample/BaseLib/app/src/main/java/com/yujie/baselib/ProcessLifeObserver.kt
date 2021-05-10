package com.yujie.baselib

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class ProcessLifeObserver(private val cxt: Context) : DefaultLifecycleObserver {

		companion object {
				const val APP_STATE = "APP_STATE"
		}

		init {
				ProcessLifecycleOwner.get().lifecycle.addObserver(this)
		}

		override fun onStart(owner: LifecycleOwner) {
				super.onStart(owner)
		}

		override fun onStop(owner: LifecycleOwner) {
				super.onStop(owner)
		}
}