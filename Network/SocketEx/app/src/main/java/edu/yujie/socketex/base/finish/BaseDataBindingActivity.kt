package edu.yujie.socketex.base.finish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseDataBindingActivity<T : ViewDataBinding> : AppCompatActivity(), IBaseBinding<T> {

    protected val TAG = javaClass.simpleName

    abstract override val layoutId: Int

    override lateinit var binding: T

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<T>(this, layoutId)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(this@BaseDataBindingActivity)
            .subscribe(onNext)


}