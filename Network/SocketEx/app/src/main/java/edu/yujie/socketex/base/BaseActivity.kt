package edu.yujie.socketex.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.functions.Functions

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), IBaseBinding<T> {

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


}