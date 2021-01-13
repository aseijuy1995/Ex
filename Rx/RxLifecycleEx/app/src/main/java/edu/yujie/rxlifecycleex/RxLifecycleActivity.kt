package edu.yujie.rxlifecycleex

import android.os.Bundle
import com.trello.rxlifecycle4.android.ActivityEvent
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.kotlin.bindToLifecycle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//https://github.com/trello/RxLifecycle
//https://www.jianshu.com/p/88ea00755a7b

class RxLifecycleActivity : RxAppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindUntilEvent<Long>(ActivityEvent.DESTROY))
//            .compose(bindToLifecycle())
            .bindToLifecycle(this)
            .subscribe {
                println("$TAG onNext() it = $it")
            }



    }

}