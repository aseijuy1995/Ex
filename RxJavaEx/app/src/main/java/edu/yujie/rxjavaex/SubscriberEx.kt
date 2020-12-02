package edu.yujie.rxjavaex

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun getSubscriber(TAG: String) = object : Subscriber<String> {
    override fun onComplete() {
        println("$TAG: onComplete()")
    }

    override fun onSubscribe(s: Subscription?) {
        println("$TAG: onSubscribe()")
    }

    override fun onNext(t: String?) {
        println("$TAG: onNext(), str = $t")
    }

    override fun onError(t: Throwable?) {
        println("$TAG: onError(), e = $t")
    }

}