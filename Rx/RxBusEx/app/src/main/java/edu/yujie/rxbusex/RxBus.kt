package edu.yujie.rxbusex

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * 封裝RxJava,通過subscribe()簡化Event傳遞,並管理Disposable
 * */
object RxBus {
    private val TAG = javaClass.simpleName
    val subject = BehaviorSubject.create<Any>().toSerialized()
    private val disposableMap: HashMap<Any, CompositeDisposable> by lazy { HashMap() }

    fun send(any: Any) = subject.onNext(any)

    inline fun <reified T : Any> observable(): Observable<T> = subject.ofType(T::class.java)

    fun register(any: Any, disposable: Disposable) {
        var compositeDisposable = disposableMap[any]
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
        disposableMap[any] = compositeDisposable
    }

    fun unRegister(any: Any) {
        val compositeDisposable = disposableMap[any]
        if (compositeDisposable == null) {
            println("$TAG unRegister() Trying to unregister subscriber that wasn't registered")
        } else {
            compositeDisposable.clear()
            disposableMap.remove(any)
        }
    }

}

fun Disposable.registerInBus(any: Any) = RxBus.register(any, this)