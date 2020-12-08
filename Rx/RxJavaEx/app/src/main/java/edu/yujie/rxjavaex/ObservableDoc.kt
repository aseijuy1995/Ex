package edu.yujie.rxjavaex

import io.reactivex.rxjava3.core.*

// Type / Hot & Cold
//https://www.jianshu.com/p/f5f327c8b612

class ObservableDoc {
    init {
        // Type
        Observable.create<Int> { }// 發射0..N數據
        Flowable.create(FlowableOnSubscribe<Int> { }, BackpressureStrategy.MISSING)// 發射0..N數據,支持backpressure
        Single.create<Int> { }// 只發射單個數據 or Error
        Maybe.create<Int> { }// 只發射Success or Error || Complete or Error
        Completable.create { }// 只發射Success or Error

        // Hot & Cold
        // Code Observable 當訂閱subscribe()時,上游才開始發射數據,下游接收
        // Hot Observable 不需訂閱,建立時即可發射數據
        val connectableObservable = Observable.just(1, 2, 3).publish()
        connectableObservable.connect()
        connectableObservable.subscribe { }

    }
}