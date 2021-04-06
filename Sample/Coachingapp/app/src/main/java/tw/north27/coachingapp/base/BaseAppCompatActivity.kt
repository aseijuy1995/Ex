package tw.north27.coachingapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.module.rx.IRxJavaSubscribe
import java.util.concurrent.TimeUnit

open class BaseAppCompatActivity<T : ViewBinding>(inflater: (LayoutInflater) -> T) : AppCompatActivity(), IRxJavaSubscribe {

    protected val binding by viewBinding(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Observable.interval(1, TimeUnit.SECONDS).subscribe {
            Timber.d("interval = $it")
        }
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