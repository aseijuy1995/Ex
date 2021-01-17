package edu.yujie.rxkotlinex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

//https://github.com/ReactiveX/RxKotlin
//https://juejin.cn/post/6844903625622290445
//https://www.jianshu.com/p/f6e7d2775bad

class RxKotlinActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_kotlin)

//        // toObservable() - Observable
//        listOf(1, 2, 3, 4, 5).toObservable()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // flatMapSequence
//        Observable.just("Chen", "Yu", "Jie")
//            .flatMapSequence {
//                Sequence { it.iterator() }
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // toMap - Map化
//        Observable.just(Pair(1, "Chen"), Pair(2, "Yu"), Pair(3, "Jie"))
//            .toMap()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // toMultimap - Map<List>化
//        Observable.just(Pair(1, "Chen"), Pair(1, "Chen2"), Pair(2, "Yu"), Pair(3, "Jie"))
//            .toMultimap()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // mergeAll - 合併(調用RxJava flatMap())
//        val observable = listOf(1, 2, 3).toObservable()
//        val observable2 = listOf(4, 5, 6).toObservable()
//        val list = listOf(observable, observable2)
//        list.toObservable()
//            .mergeAll()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // concatAll - 有序合併(調用RxJava concatMap())
//        val observable = listOf(1, 2, 3).toObservable()
//        val observable2 = listOf(4, 5, 6).toObservable()
//        val list = listOf(observable, observable2)
//        list.toObservable()
//            .concatAll()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // switchLatest - 從最後發出的Observable發出
//        val subject = ReplaySubject.create<Observable<Int>>()
//        subject.onNext(Observable.just(1, 2, 3))
//        subject.onNext(Observable.just(4, 5, 6))
//        subject.switchLatest()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // cast - 修正型別
//        Observable.just(1, 3, 2)
//            .cast<Int>()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // ofType - 過濾型別
//        Observable.just(1, 2, 3, "4", "5")
//            .ofType<Int>()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // merge - 合併Observable / Flowable
//        val list = mutableListOf<Observable<Int>>()
//        list.add(Observable.just(1, 2, 3))
//        list.add(Observable.just(4, 5, 6))
//        list.merge()
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // mergeDelayError - 合併
//        val list = mutableListOf<Observable<Int>>()
//        list.add(Observable.just(1, 2, 3))
//        list.add(Observable.just(4, 5, 6))
//        list.mergeDelayError()
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // toFlowable - Flowable
//        listOf(1, 2, 3).toFlowable()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // mergeAllSingles -  合併觀察者發出的所有Single
//        val subject = ReplaySubject.create<Single<Int>>()
//        subject.onNext(Single.just(1))
//        subject.onNext(Single.just(2))
//        subject.onNext(Single.just(3))
//        subject.mergeAllSingles()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // mergeAllMaybes -  合併觀察者發出的所有Maybe
//        val subject = ReplaySubject.create<Maybe<Int>>()
//        subject.onNext(Maybe.just(1))
//        subject.onNext(Maybe.just(2))
//        subject.onNext(Maybe.just(3))
//        subject.mergeAllMaybes()
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // toCompletable - Completable
//        val action = { println("$TAG Action") }
//        action.toCompletable()
//            .subscribe {
//                println("$TAG onComplete()")
//            }

        //--------------------------------------------------------------------------------

//        // mergeAllCompletables - 合併觀察者發出的所有mergeAllCompletables
//        val subject = BehaviorSubject.create<Completable>()
//        val subject2 = BehaviorSubject.create<Completable>()
//        subject.onComplete()
//        subject2.onComplete()
//        subject.mergeWith(subject2)
//            .mergeAllCompletables()
//            .subscribe {
//                println("$TAG onComplete()")
//            }

        //--------------------------------------------------------------------------------

//        // blockingSubscribeBy
//        Observable.just(1, 2, 3)
//            .delay(1, java.util.concurrent.TimeUnit.SECONDS)
//            .blockingSubscribeBy {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        //  addTo - 將disposable增加至CompositeDisposable
//        Observable.just(1, 2, 3)
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }.addTo(compositeDisposable)

        //--------------------------------------------------------------------------------

//        // plusAssign - 增加disposable至CompositeDisposable
//        val disposable = Observable.just(1, 2, 3)
//            .subscribeBy {
//                println("$TAG onNext() it = $it")
//            }
//        compositeDisposable.plusAssign(disposable)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}