package edu.yujie.socketex.finish.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.finish.inter.IRxJavaSubscribe
import edu.yujie.socketex.finish.util.DisposablesLifeObs
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseAppCompatActivity : AppCompatActivity(), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //CompositeDisposable
        DisposablesLifeObs(this, compositeDisposable)
    }

    override fun <T> Observable<T>.subscribeWithLife(): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe()

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext)

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext, onError)

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable? =
        bindToLifecycle(this@BaseAppCompatActivity)
            .subscribe(onNext, onError, onComplete)
}