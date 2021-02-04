package edu.yujie.socketex.finish.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DisposablesLifeObs(private val owner: LifecycleOwner, private val disposables: CompositeDisposable) : DefaultLifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        disposables.clear()
    }

}