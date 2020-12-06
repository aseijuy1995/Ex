package edu.yujie.rxjavaex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.reactivestreams.Subscription

//https://github.com/ReactiveX/RxJava
//https://gank.io/post/560e15be2dca930e00da1083
//https://www.jianshu.com/p/464fa025229e
//https://juejin.cn/post/6844903885245513741
//https://blog.csdn.net/LucasXu01/article/details/105279367

class RxJavaActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private var disposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()
    private var subscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // Observable
//        Observable.just(1, 2, 3)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // Flowable - backpressure
//        Flowable.just(1, 2, 3)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // Single
//        Single.just(1)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                println("$TAG onSuccess() it = $it")
//            }, {
//                println("$TAG onError() Throwable = $it")
//            })

//        // Maybe
//        Maybe.create<Int> {
//            it.onSuccess(1)
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // Completable
//        Completable.create {
//            it.onComplete()
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                println("$TAG onCompletable()")
//            }

        //--------------------------------------------------------------------------------

//        // Flowable最佳做法,保證數據不丟失 & 觸發OOM
//        var subscription: Subscription? = null
//        Flowable.create(FlowableOnSubscribe<Int> {
//            var i = 0
//            while (true) {
//                println("$TAG create() $i requested = ${it.requested()}")
//                if (it.requested().toInt() == 0) continue
//                it.onNext(++i)
//            }
//
//        }, BackpressureStrategy.MISSING)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(Schedulers.newThread())
//            .subscribe(object : Subscriber<Int> {
//                override fun onSubscribe(s: Subscription?) {
//                    println("$TAG onSubscribe()")
//                    s?.request(1)
//                    subscription = s
//                }
//
//                override fun onNext(t: Int?) {
//                    sleep(50)
//                    println("$TAG onNext() t = $t")
//                    subscription?.request(1)
//                }
//
//                override fun onError(t: Throwable?) {
//                    println("$TAG onError()")
//                }
//
//                override fun onComplete() {
//                    println("$TAG onComplete()")
//                }
//
//            })

        //--------------------------------------------------------------------------------

//        // Cold Observable
//        val connectableObservable = Observable.just(1, 2, 3).publish()
//
//        findViewById<TextView>(R.id.tv_view).setOnClickListener {
//            connectableObservable.connect()
//        }
//        connectableObservable.subscribe { println("$TAG tv_view $it") }
//
//        findViewById<TextView>(R.id.tv_view2).setOnClickListener {
//            connectableObservable.subscribe { println("$TAG tv_view2 $it") }
//        }

        //--------------------------------------------------------------------------------

//        val observer = object : Observer<Int> {
//            override fun onSubscribe(d: Disposable?) {
//                println("$TAG onSubscribe()")
//                disposable = d
//            }
//
//            override fun onNext(t: Int?) {
//                println("$TAG onNext() t = $t")
//            }
//
//            override fun onError(e: Throwable?) {
//                println("$TAG onError()")
//            }
//
//            override fun onComplete() {
//                println("$TAG onComplete()")
//            }
//
//        }

//        // ReplaySubject
//        val subject = ReplaySubject.create<Int>()
//        subject.onNext(1)
//        subject.subscribe(observer)
//        subject.onNext(2)

//        // PublishSubject
//        val subject = PublishSubject.create<Int>()
//        subject.onNext(1)//not execute
//        subject.subscribe(observer)
//        subject.onNext(2)

//        // BehaviorSubject
//        val subject = BehaviorSubject.create<Int>()
//        subject.onNext(1)//not execute
//        subject.onNext(2)
//        subject.subscribe(observer)
//        subject.onNext(3)

//        // AsyncSubject
//        val subject = AsyncSubject.create<Int>()
//        subject.onNext(1)
//        subject.subscribe(observer)
//        subject.onNext(2)
//        subject.onComplete()

        //--------------------------------------------------------------------------------

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}
