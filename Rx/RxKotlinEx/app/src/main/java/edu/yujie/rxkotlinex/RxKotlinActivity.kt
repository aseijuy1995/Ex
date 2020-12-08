package edu.yujie.rxkotlinex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

//https://github.com/ReactiveX/RxKotlin
//https://www.jianshu.com/p/f6e7d2775bad
//https://juejin.cn/post/6844903625622290445
//https://blog.csdn.net/vitaviva/article/details/105253098

class RxKotlinActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // toObservable() - Observable
//        listOf(1, 2, 3, 4, 5).toObservable()
//            .filter { it >= 3 }
//            .subscribeBy(
//                onNext = {
//                    println("$TAG onNext() it = $it")
//                },
//                onError = {
//                    println("$TAG onError() it = $it")
//                },
//                onComplete = {
//                    println("$TAG onComplete()")
//                }
//            )

//        // flatMapSequence
//        Observable.just("Chen", "Yu", "Jie")
//            .flatMapSequence {
//                Sequence {
//                    it.iterator()
//                }
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // toMap - Map化
//        Observable.just(Pair(1, "Chen"), Pair(2, "Yu"), Pair(3, "Jie"))
//            .toMap()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//        // toMultimap - Map化
//        Observable.just(Pair(1, "Chen"), Pair(1, "Chen2"), Pair(2, "Yu"), Pair(3, "Jie"))
//            .toMultimap()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//        // mergeAll - 合併
//        val subject = PublishSubject.create<Observable<Int>>()
//        subject.mergeAll()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        subject.onNext(Observable.just(1, 2, 3))
//        subject.onNext(Observable.just(4, 5, 6))

//        // concatAll - 有序合併
//        val subject = PublishSubject.create<Observable<Int>>()
//        subject.concatAll()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        subject.onNext(Observable.just(1, 2, 3))
//        subject.onNext(Observable.just(4, 5, 6))

//        // switchLatest - 從最後發出的Observable發出
//        val subject = PublishSubject.create<Observable<Int>>()
//        subject.switchLatest()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        subject.onNext(Observable.just(1, 2, 3))
//        subject.onNext(Observable.just(4, 5, 6))

//        // cast - 修正型別
//        Observable.just(1, 3, 2)
//            .cast<Int>()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//        // ofType - 過濾型別
//        Observable.just(1, 2, 3, "4", "5")
//            .ofType<Int>()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//         // merge - 合併
//        val list = mutableListOf<Observable<Int>>()
//        list.add(Observable.just(1, 2, 3))
//        list.add(Observable.just(4, 5, 6))
//        list.merge()
//        list.map {
//            it.subscribe {
//                println("$TAG onNext() it = $it")
//            }
//        }

//        // mergeDelayError - 合併
//        val list = mutableListOf<Observable<Int>>()
//        list.add(Observable.just(1, 2, 3))
//        list.add(Observable.just(4, 5, 6))
//        list.mergeDelayError()
//        list.map {
//            it.subscribe {
//                println("$TAG onNext() it = $it")
//            }
//        }

//        // toFlowable - Flowable
//        listOf(1, 2, 3)
//            .toFlowable()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//        // mergeAllSingles -  合併觀察者發出的所有Single
//        val subject = PublishSubject.create<Single<Int>>()
//        subject.mergeAllSingles()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        subject.onNext(Single.just(1))
//        subject.onNext(Single.just(2))
//        subject.onNext(Single.just(3))

//        // mergeAllSingles -  合併觀察者發出的所有Maybe
//        val subject = PublishSubject.create<Maybe<Int>>()
//        subject.mergeAllMaybes()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        subject.onNext(Maybe.just(1))
//        subject.onNext(Maybe.just(2))
//        subject.onNext(Maybe.just(3))

//        // toCompletable - Completable
//        Action { println("$TAG Action") }
//            .toCompletable()
//            .delay(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext()")
//            }

        // mergeAllCompletables

//        // blockingSubscribeBy
//        Observable.just(1, 2, 3)
//            .delay(1, TimeUnit.SECONDS)
//            .blockingSubscribeBy {
//                println("$TAG onNext() it = $it")
//            }

//        //  addTo - 將disposable增加至CompositeDisposable
//        Observable.just(1, 2, 3)
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }.addTo(compositeDisposable)

//        // plusAssign - 增加disposable至CompositeDisposable
//        val disposable = Observable.just(1, 2, 3)
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        compositeDisposable.plusAssign(disposable)

//        // combineLatest
//        val observable = Observable.just(1, 2, 3,4)
//        val observable2 = Observable.just(4, 5, 6)
//        Observables.combineLatest(observable, observable2)
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}