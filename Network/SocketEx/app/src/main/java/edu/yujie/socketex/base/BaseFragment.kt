package edu.yujie.socketex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.annotations.CheckReturnValue
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.annotations.SchedulerSupport
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.functions.Functions

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), IBaseBinding<T> {

    protected val TAG = javaClass.simpleName

    abstract override val layoutId: Int

    override lateinit var binding: T

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected abstract fun initView()

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }


}