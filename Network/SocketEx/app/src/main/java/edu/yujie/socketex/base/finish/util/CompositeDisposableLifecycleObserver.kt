package edu.yujie.socketex.base.finish.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CompositeDisposableLifecycleObserver(private val owner: LifecycleOwner, private val compositeDisposable: CompositeDisposable) : DefaultLifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        compositeDisposable.clear()
    }

}