package edu.yujie.rxjavaex

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun getObserverInt(TAG:String) = object : Observer<Int> {
    override fun onComplete() {
        println("$TAG: onComplete()")
    }

    override fun onSubscribe(d: Disposable?) {
        println("$TAG: onSubscribe()")
    }

    override fun onNext(@NonNull t: @NonNull Int?) {
        println("$TAG: onNext(), str = $t")
    }

    override fun onError(e: Throwable?) {
        println("$TAG: onError(), e = $e")
    }

}

fun getObserverList(TAG:String) = object:Observer<List<Int>>{
    override fun onComplete() {
        println("$TAG: onComplete()")
    }

    override fun onSubscribe(d: Disposable?) {
        println("$TAG: onSubscribe()")
    }

    override fun onNext(t: List<Int>?) {
        println("$TAG: onNext(), list = $t")
    }

    override fun onError(e: Throwable?) {
        println("$TAG: onError(), e = $e")
    }

}