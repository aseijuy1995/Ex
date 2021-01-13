package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    val TAG = javaClass.simpleName

    lateinit var binding: T

    abstract val layoutId: Int

    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }
}