package com.yujie.core_lib.ext

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle4.LifecycleProvider
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindUntilEvent
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import com.trello.rxlifecycle4.kotlin.bindUntilEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * LifecycleOwner
 * */
fun <T> Observable<T>.observe(owner: LifecycleOwner): Disposable? {
		return bindToLifecycle(owner).subscribe()
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, onNext: (T) -> Unit) {
		this.bindToLifecycle(owner).subscribe(onNext)
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? {
		return bindToLifecycle(owner).subscribe(onNext, onError)
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable? {
		return bindToLifecycle(owner).subscribe(onNext, onError, onComplete)
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, obs: Observer<T>) {
		bindToLifecycle(owner).subscribe(obs)
}

/**
 * Lifecycle.Event
 * */
fun <T> Observable<T>.observe(view: View): Disposable? {
		return bindToLifecycle(view).subscribe()
}

fun <T> Observable<T>.observe(owner: LifecycleOwner, event: Lifecycle.Event): Disposable? {
		return bindUntilEvent(owner, event).subscribe()
}

fun <T> Observable<T>.observe(lifecycle: Observable<Lifecycle.Event>, event: Lifecycle.Event): Disposable? {
		return bindUntilEvent(lifecycle, event).subscribe()
}

/**
 * LifecycleProvider
 * */
fun <T> Observable<T>.observe(provider: LifecycleProvider<Lifecycle.Event>, event: Lifecycle.Event): Disposable? {
		return bindUntilEvent(provider, event).subscribe()
}

fun <T> Observable<T>.observe(provider: LifecycleProvider<Lifecycle.Event>): Disposable? {
		return bindToLifecycle(provider).subscribe()
}