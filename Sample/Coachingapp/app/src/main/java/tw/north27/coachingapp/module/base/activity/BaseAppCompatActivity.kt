package tw.north27.coachingapp.module.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import tw.north27.coachingapp.ext.startDisposablesLifeObs
import tw.north27.coachingapp.module.rx.IRxJavaSubscribe

/**
 * 已測試
 * */
open class BaseAppCompatActivity() : AppCompatActivity(), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable.startDisposablesLifeObs(this)
    }

    override fun <T> Observable<T>.subscribeWithRxLife(): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe()

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext)

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext, onError)

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext, onError, onComplete)

}