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

//        // create
//        Observable.create<Int> {
//            it.onNext(1)
//            it.onNext(2)
//            it.onNext(3)
//        }.subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // from
//        Observable.fromArray(listOf(1, 2, 3))
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // just
//        Observable.just(1, 2, 3)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // defer - 確保訂閱後發射
//        val observable = Observable.defer {
//            Observable.just(1, 2, 3)
//        }
//        observable.subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // range
//        Observable.range(1, 10)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // interval - 定時發射
//        Observable.interval(3, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it =$it")
//            }

//        // repeat
//        Observable.just(1, 2, 3).repeat(3)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // timer - 延遲發射(發射數據只有一次)
//        Observable.timer(3, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

        //--------------------------------------------------------------------------------

//        // skip - 從前頭掠過n個數據
//        Observable.just(1, 2, 3)
//            .skip(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // skipLast - 從後頭掠過n個數據
//        Observable.just(1, 2, 3)
//            .skipLast(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // debounce
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_500)
//            it.onNext(2)
//            sleep(500)
//            it.onNext(3)
//        }.debounce(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // distinct - 過濾重複
//        Observable.just(1, 2, 3, 1, 2)
//            .distinct()
//            .subscribe {
//                println("$TAG onNext() it $it")
//            }

//        // distinctUntilChanged - 過濾相鄰重複數據
//        Observable.just(1,2,3,3,2,1)
//            .distinctUntilChanged()
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // elementAt
//        Observable.just(1, 2, 3)
//            .elementAt(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // filter
//        Observable.just(1, 2, 3)
//            .filter {
//                it % 2 == 0
//            }.subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // first
//        Observable.just(1, 2, 3)
//            .first(0)
//            .subscribe({
//                println("$TAG onNext() it = $it")
//            }, {
//                println("$TAG onError() it = $it")
//            })

//        // last
//        Observable.just(1, 2, 3)
//            .last(0)
//            .subscribe({
//                println("$TAG onNext() it = $it")
//            }, {
//                println("$TAG onError() it = $it")
//            })

//        // ignoreElements
//        Observable.just(1, 2, 3)
//            .ignoreElements()
//            .subscribe({
//                println("$TAG onComplete()")
//            }, {
//                println("$TAG onError() ")
//            })

//        // ofType
//        Observable.just(1, 2, 2.5, 3)
//            .ofType(Integer::class.java)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // sample - 設置時間週期更新
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1500)
//            it.onNext(2)
//            sleep(2000)
//            it.onNext(3)
//        }.sample(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleFirst - 指定時間內丟出最新數據
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_000)
//            it.onNext(2)
//            sleep(300)
//            it.onNext(3)
//            sleep(1_200)
//            it.onNext(4)
//        }.throttleFirst(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleLast - 同於sample
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(1_200)
//            it.onNext(2)
//            sleep(300)
//            it.onNext(3)
//            sleep(1_200)
//            it.onNext(4)
//        }.throttleLast(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // throttleLast - 同於throttleLast
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(300)
//            it.onNext(2)
//            sleep(800)
//            it.onNext(3)
//            sleep(800)
//            it.onNext(4)
//        }.throttleLatest(1, TimeUnit.SECONDS)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // take - 發射前n個數據
//        Observable.just(1, 2, 3)
//            .take(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // takeLast - 發射後n個數據
//        Observable.just(1, 2, 3)
//            .takeLast(2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // timeout
//        Observable.create<Int> {
//            it.onNext(1)
//            sleep(300)
//            it.onNext(2)
//            sleep(1200)
//            it.onNext(3)
//        }.timeout(1, TimeUnit.SECONDS)
//            .subscribe(object : Observer<Int> {
//                override fun onSubscribe(d: Disposable?) {
//                }
//
//                override fun onNext(t: Int?) {
//                    println("$TAG onNext() t = $t")
//                }
//
//                override fun onError(e: Throwable?) {
//                    println("$TAG onError() e = $e")
//                }
//
//                override fun onComplete() {
//                }
//
//            })

//        // merge - 無序合併
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.merge(observable, observable2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // concat - 有序合併
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.concat(observable, observable2)
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // zip
//        val observable = Observable.just(1, 2, 3)
//        val observable2 = Observable.just(4, 5, 6)
//        Observable.zip(observable, observable2, BiFunction { i: Int, i2: Int ->
//            i + i2
//        }).subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // startWith
//        Observable.just(1, 2, 3)
//            .startWith(Observable.just(4, 5, 6))
//            .subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // join - 時間期限核定
//        Observable.just(1, 2, 3)
//            .join(Observable.just(1, 10, 100), Function<Int, ObservableSource<Long>> {
//                Observable.timer(1, TimeUnit.SECONDS)
//            }, Function<Int, ObservableSource<Long>> {
//                Observable.timer(2, TimeUnit.SECONDS)
//            }, BiFunction<Int, Int, Int> { i: Int, i2: Int ->
//                i + i2
//            }).subscribe {
//                println("$TAG onNext() it = $it")
//            }

//        // combineLatest - 取得最後一位做合併
//        val observable = Observable.just(1, 10,100)
//        val observable2 = Observable.interval(1, TimeUnit.SECONDS)
//        Observable.combineLatest(observable, observable2, BiFunction<Int, Long, Int> { i1: Int, i2: Long ->
//            (i1 + i2).toInt()
//        }).subscribe {
//            println("$TAG onNext() it = $it")
//        }

//        // switchOnNext
//        Observable.switchOnNext<Int> {
//            it.onNext(Observable.just(1, 2, 3))
//            it.onNext(Observable.just(4, 5, 6))
//        }.subscribe {
//            println("$TAG onNext() it = $it")
//        }


        //--------------------------------------------------------------------------------
        //--------------------------------------------------------------------------------

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}
