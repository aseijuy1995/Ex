package edu.yujie.rxjavaex

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers

fun getFlowable(TAG: String): Flowable<Int> = Flowable.range(1, 10)
    .observeOn(Schedulers.io())
    .map {
        println("$TAG: map() it = $it")
        it * it
    }

fun getFlowParallel(TAG: String): Flowable<Int> = Flowable.range(1, 10)
    .parallel()
    .runOn(Schedulers.computation())
    .map {
        println("$TAG: map() it = $it")
        it * it
    }
    .sequential()