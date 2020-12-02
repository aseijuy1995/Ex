package edu.yujie.rxkotlinex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//https://www.jianshu.com/p/f6e7d2775bad
//https://www.jianshu.com/p/240f1c8ebf9d
class RxKotlinActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        listOf("Alpha", "Beta", "Gamma", "Delta", "Epsilon").toObservable()
//                .filter {
//                    it.length >= 5
//                }
//                .subscribeBy(
//                        onNext = { println("$TAG: onNext() it = $it") },
//                        onError = { println("$TAG: onError() e = $it") },
//                        onComplete = { println("$TAG: onComplete()") }
//                )

//        listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f).toObservable()
//                .subscribeBy(
//                        onNext = { println("$TAG:onNext() $it") },
//                        onError = { println("$TAG:onError() $it") },
//                        onComplete = { println("$TAG:onComplete()") }
//                )

//        val subject = PublishSubject.create<Int>()
//        subject.map {
//            if (it % 2 == 0) "Even" else "Odd"
//        }.subscribe {
//            println("$TAG: $it")
//        }
//        subject.onNext(4)
//        subject.onNext(9)

//        // maybe
//        val maybeValue = Maybe.just(4)
//        maybeValue.subscribeBy(
//                onSuccess = { println("$TAG:onSuccess() $it") },
//                onError = { println("$TAG:onError() $it") },
//                onComplete = { println("$TAG:onComplete()") }
//        )

//        // create
//        val observable = Observable.create<Int> {
//            it.onNext(1)
//        }
//        observable.subscribe{
//            println("$TAG: onNext() $it")
//        }

//        // from
//        Observable.fromIterable(listOf(1,2,3,4))
//                .subscribeBy(
//                        onNext = { println("$TAG: onNext() $it")},
//                        onComplete = { println("$TAG: onComplete()")}
//                )

//        // empty
//        Observable.empty<Int>().subscribe {
//            println("$TAG: $it")
//        }

//        // interval
//        Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeBy {
//                    println("$TAG: interval() $it")
//                }

//        // timer
//        Observable.timer(5,TimeUnit.SECONDS)
//                .subscribeBy {
//                    println("$TAG: timer() $it")
//                }

//        // range
//        Observable.range(1, 3)
//                .subscribeBy {
//                    println("$TAG: range() $it")
//                }

//        // disposable
//        Observable.interval(1, TimeUnit.SECONDS)
//                .map {
//                    it.toInt()
//                }
//                .subscribe(object : Observer<Int> {
//                    var disposable: Disposable? = null
//                    override fun onComplete() {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun onSubscribe(d: Disposable?) {
//                        disposable = d
//                    }
//
//                    override fun onNext(@NonNull t: Int) {
//                        if (t == 5 && disposable != null) {
//                            disposable?.dispose()
//                        }
//                        println("$TAG: interval() $t")
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        TODO("Not yet implemented")
//                    }
//
//                })

//        // connect
//        val connectableObservable = listOf(1, 2, 3, 4).toObservable().publish()
//        connectableObservable.subscribe { println("$TAG subscribe1:$it") }
//        connectableObservable.subscribe { println("$TAG subscribe2:$it") }
//        connectableObservable.connect()
//        connectableObservable.subscribe { println("$TAG subscribe3:$it") }

//        //PublishSubject - code observable> hot observable (Ex: Channel) 只發出訂閱後的數據
//        val observable = Observable.interval(1, TimeUnit.SECONDS)
//        val subject = PublishSubject.create<Long>()
//        observable.subscribe(subject)
//        subject.subscribe { println("$TAG subscribe $it") }
//        Thread.sleep(2000)
//        subject.subscribe { println("$TAG subscribe2 $it") }
//        Thread.sleep(3000)

//        //AsyncSubject - 丟出執行onComplete()前的最後一個值
//        val subject = AsyncSubject.create<Int>()
//        val observable = Observable.just(1, 2, 3, 4)
//        val observer = object : Observer<Int> {
//            override fun onComplete() {
//                println("$TAG: onComplete()")
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                println("$TAG: onSubscribe()")
//            }
//
//            override fun onNext(t: Int?) {
//                println("$TAG: onNext() $t")
//            }
//
//            override fun onError(e: Throwable?) {
//                println("$TAG: onError() $e")
//            }
//
//        }
//        observable.subscribe(subject)
//        subject.subscribe(observer)

//        //BehaviorSubject - 丟出訂閱前的最後一個值,再繼續接收發射的數據
//        val subject = BehaviorSubject.create<Int>()
//        val observer = object : Observer<Int> {
//            override fun onComplete() {
//                println("$TAG onComplete()")
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                println("$TAG onSubscribe()")
//            }
//
//            override fun onNext(t: Int?) {
//                println("$TAG onNext() t = $t")
//            }
//
//            override fun onError(e: Throwable?) {
//                println("$TAG onError() e = $e")
//            }
//        }
//        val observer2 = object : Observer<Int> {
//            override fun onComplete() {
//                println("$TAG onComplete2()")
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                println("$TAG onSubscribe2()")
//            }
//
//            override fun onNext(t: Int?) {
//                println("$TAG onNext2() t = $t")
//            }
//
//            override fun onError(e: Throwable?) {
//                println("$TAG onError2() e = $e")
//            }
//        }
//        subject.onNext(1)
//        subject.onNext(2)
//        subject.subscribe(observer)
//        subject.onNext(3)
//        subject.subscribe(observer2)
//        subject.onNext(4)

//        //ReplaySubject - 不論何時發射,數據全部都送出
//        val subject = ReplaySubject.create<Int>()
//        val observer = object : Observer<Int> {
//            override fun onComplete() {
//                println("$TAG onComplete2()")
//            }
//
//            override fun onSubscribe(d: Disposable?) {
//                println("$TAG onSubscribe2()")
//            }
//
//            override fun onNext(t: Int?) {
//                println("$TAG onNext2() t = $t")
//            }
//
//            override fun onError(e: Throwable?) {
//                println("$TAG onError2() e = $e")
//            }
//        }
//        subject.onNext(1)
//        subject.onNext(2)
//        subject.subscribe(observer)
//        subject.onNext(3)
//        subject.onNext(4)
//        subject.subscribe(observer)

    }
}