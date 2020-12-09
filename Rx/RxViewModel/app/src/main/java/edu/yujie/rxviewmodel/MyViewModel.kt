package edu.yujie.rxviewmodel

import edu.yujie.rxlifecycleex.ViewModelEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MyViewModel : RxViewModel() {

    val dataObservable = Observable.interval(1, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
//        .bindToLifecycle(this)
        .compose(bindUntilEvent(ViewModelEvent.ACTIVE))
}