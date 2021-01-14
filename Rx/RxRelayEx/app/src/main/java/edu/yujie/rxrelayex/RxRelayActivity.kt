package edu.yujie.rxrelayex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import com.jakewharton.rxrelay3.ReplayRelay
import com.yujie.rxrelayex.R
import com.yujie.rxrelayex.databinding.ActivityRxrelayBinding
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

//https://github.com/JakeWharton/RxRelay
//https://habr.com/ru/company/ozontech/blog/513056/

/**
 * BehaviorRelay - 同BehaviorSubject,相異於缺少onComplete()/onError方法
 * PublishRelay - 同PublishSubject,相異於缺少onComplete()/onError方法
 * ReplayRelay - 同ReplaySubject,相異於缺少onComplete()/onError方法
 *
 * 結論：RxRelay和Subject之間的唯一區別是缺少兩個方法onComplete和onError，因此開發人員無法調用終端狀態。
 * */

class RxRelayActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityRxrelayBinding

    private val behaviorRelay = BehaviorRelay.create<Int>()

    private val publishRelay = PublishRelay.create<Int>()

    private val replayRelay = ReplayRelay.create<Int>()

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRxrelayBinding>(this, R.layout.activity_rxrelay)

        binding.btnAccept.setOnClickListener {
//            behaviorRelay.accept(count++)
//            publishRelay.accept(count++)
            replayRelay.accept(count++)
        }

        binding.btnSubscribe.setOnClickListener {
//            behaviorRelay.subscribe(object : Observer<Int> {
//            publishRelay.subscribe(object : Observer<Int> {
            replayRelay.subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable?) {
                    println("$TAG onSubscribe()")
                }

                override fun onNext(t: Int?) {
                    println("$TAG onNext() t = $t")
                }

                override fun onError(e: Throwable?) {
                    println("$TAG onError() e = ${e?.message}")
                }

                override fun onComplete() {
                    println("$TAG onComplete()")
                }

            })
        }

    }

}