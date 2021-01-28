package edu.yujie.socketex.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.base.finish.IBaseBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment(), IBaseBinding<T> {

    protected val TAG = javaClass.simpleName

    abstract override val layoutId: Int

    override lateinit var binding: T

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(this@BaseDialogFragment)
            .subscribe(onNext)

}