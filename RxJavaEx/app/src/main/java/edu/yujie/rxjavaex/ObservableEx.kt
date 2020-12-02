package edu.yujie.rxjavaex

import io.reactivex.rxjava3.core.Observable

fun getObservableCreate(): Observable<Int> = Observable.create<Int> {
    it.onNext(1)
    it.onNext(2)
    it.onNext(3)
    it.onComplete()
}

fun getObservableJust(): Observable<Int> = Observable.just(1, 2, 3, 4)

fun getObservableFrom(): Observable<List<Int>> = Observable.fromArray(listOf(1, 2, 3, 4))

fun getObservableMap(): Observable<Int> = Observable.just(1, 2, 3, 4).map {
    it * it
}

fun getObservableFlatMap(): Observable<List<Int>> = Observable.just(1, 2, 3, 4).flatMap {
    Observable.fromArray(listOf(it * it, it * it, it * it))
}

fun getObservableCompose() = Observable.just(1, 2, 3, 4).compose {
    it.map { it * it }
        .flatMap { Observable.fromArray(listOf(it * it, it * it, it * it)) }
}