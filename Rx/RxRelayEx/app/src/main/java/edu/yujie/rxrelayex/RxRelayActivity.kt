package edu.yujie.rxrelayex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yujie.rxrelayex.R
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

//https://github.com/JakeWharton/RxRelay
//https://habr.com/ru/company/ozontech/blog/513056/

//結論：RxRelay和Subject之間的唯一區別是缺少兩個方法onComplete和onError，因此開發人員無法調用終端狀態。

class RxRelayActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observer = object : Observer<Int> {
            override fun onSubscribe(d: Disposable?) {
                println("$TAG onSubscribe()")
            }

            override fun onNext(t: Int?) {
                println("$TAG onNext() t = $t")
            }

            override fun onError(e: Throwable?) {
                println("$TAG onError()")
            }

            override fun onComplete() {
                println("$TAG onComplete()")
            }

        }

//        // PublishRelay
//        val relay = PublishRelay.create<Int>()
//        relay.accept(1)//not execute
//        relay.subscribe(observer)
//        relay.accept(2)

//        // ReplayRelay
//        val relay = ReplayRelay.create<Int>()
//        relay.accept(1)
//        relay.subscribe(observer)
//        relay.accept(2)

//        //BehaviorRelay
//        val relay = BehaviorRelay.create<Int>()
//        relay.accept(1)//not execute
//        relay.accept(2)
//        relay.subscribe(observer)
//        relay.accept(3)


    }
}