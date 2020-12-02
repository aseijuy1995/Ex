package edu.yujie.rxjavaex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

//https://gank.io/post/560e15be2dca930e00da1083
//https://blog.csdn.net/LucasXu01/article/details/105279367
//https://juejin.cn/post/6844903454067032071
class RxJavaActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSubscriber(TAG)

//        // create
//        val observable = getObservableCreate()
//        val observer = getObserver(TAG)
//        observable.subscribe(observer)

//        // just
//        val observable = getObservableJust()
//        val observer = getObserver(TAG)
//        observable.subscribe(observer)

//        // from
//        val observable = getObservableFrom()
//        val observer = getObserverList(TAG)
//        observable.subscribe(observer)

//        // map
//        val observable = getObservableMap()
//        val observer = getObserverInt(TAG)
//        observable.subscribe(observer)

//        // flatMap
//        val observable = getObservableFlatMap()
//        val observer = getObserverList(TAG)
//        observable.subscribe(observer)

//        // compose
//        val observable = getObservableCompose()
//        val observer = getObserverList(TAG)
//        observable.subscribe(observer)

//        // doOnSubscribe
//        val observable = getObservableJust()
//        val observer = getObserverInt(TAG)
//        observable.doOnSubscribe {
//            println("$TAG: doOnSubscribe()")
//        }.subscribe(observer)

//        // flowable
//        val flowable = getFlowable(TAG)
//        flowable.blockingSubscribe {
//            println("$TAG: blockingSubscribe: it = $it")
//        }

//        // flowable parallel - 併發
//        val flowable = getFlowParallel(TAG)
//        flowable.blockingSubscribe {
//            println("$TAG blockingSubscribe: it = $it")
//        }


        val observable = getObservableJust()
        val disposable = observable.subscribe {
            println("$TAG: it = $it")
        }
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.add(disposable)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}
