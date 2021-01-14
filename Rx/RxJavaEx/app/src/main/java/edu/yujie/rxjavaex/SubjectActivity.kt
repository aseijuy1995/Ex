package edu.yujie.rxjavaex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.rxjavaex.databinding.ActivitySubjectBinding
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.*
import java.io.IOException

//https://blog.csdn.net/mq2553299/article/details/78848773
//https://www.jianshu.com/p/1257c8ba7c0c
//https://www.jianshu.com/p/240f1c8ebf9d
//https://prototypez.github.io/2016/04/30/rxjava-common-mistakes-1/

/**
 * ReplaySubject - 訂閱subscribe()時,將訂閱前後的所有數據發送給觀察者
 * BehaviorSubject - 訂閱subscribe()時,將訂閱前的最後一個數據&訂閱後的所有數據發送的給觀察者
 * PublishSubject - 訂閱subscribe()時,將訂閱後的所有數據發送給觀察者
 * AsyncSubject - 當執行onComplete()時,發送最後一個數據
 * UnicastSubject - 僅支持訂閱1次,其餘規則與ReplaySubject相同,若繼續訂閱會執行onError()
 * */
class SubjectActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivitySubjectBinding

    private val replaySubject = ReplaySubject.create<String>()

    private val behaviorSubject = BehaviorSubject.create<String>()

    private val publishSubject = PublishSubject.create<String>()

    private val asyncSubject = AsyncSubject.create<String>()

    private val unicastSubject = UnicastSubject.create<String>()

    private var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySubjectBinding>(this, R.layout.activity_subject)

        binding.btnNext.setOnClickListener {
//            replaySubject.onNext("next")
//            behaviorSubject.onNext("${count++}")
//            publishSubject.onNext("${count++}")
//            asyncSubject.onNext("${count++}")
            unicastSubject.onNext("${count++}")
        }

        binding.btnComplete.setOnClickListener {
//            replaySubject.onComplete()
//            behaviorSubject.onComplete()
//            publishSubject.onComplete()
//            asyncSubject.onComplete()
            unicastSubject.onComplete()
        }

        binding.btnError.setOnClickListener {
//            replaySubject.onError(IOException("IOException"))
//            behaviorSubject.onError(IOException("IOException"))
//            publishSubject.onError(IOException("IOException"))
//            asyncSubject.onError(IOException("IOException"))
            unicastSubject.onError(IOException("IOException"))
        }

        binding.btnSubscribe.setOnClickListener {
//            replaySubject.subscribe(object : Observer<String> {
//            behaviorSubject.subscribe(object : Observer<String> {
//            publishSubject.subscribe(object : Observer<String> {
//            asyncSubject.subscribe(object : Observer<String> {
            unicastSubject.subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                    println("$TAG onSubscribe()")
                }

                override fun onNext(t: String?) {
                    println("$TAG onNext() it = $t")
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