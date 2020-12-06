package edu.yujie.rxjavaex

import io.reactivex.rxjava3.disposables.CompositeDisposable

//https://www.jianshu.com/p/2a882604bbe8

class DisposableDoc {
    init {
        CompositeDisposable()//使用RxJava時為防止記憶體洩漏,管理Disposable類使用
    }
}