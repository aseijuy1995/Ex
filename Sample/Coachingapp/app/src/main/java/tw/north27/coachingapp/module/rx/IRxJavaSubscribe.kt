package tw.north27.coachingapp.module.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * 未測試
 * */
interface IRxJavaSubscribe {

    fun <T> Observable<T>.subscribeWithRxLife(): Disposable?

    fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit): Disposable?

    fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable?

    fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable?

}