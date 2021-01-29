package edu.yujie.socketex.base.finish.inter

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

interface IRxJavaSubscribe {

    fun <T> Observable<T>.subscribeWithLife(): Disposable?
//
    fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit): Disposable?
//
    fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable?

    fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable?

}