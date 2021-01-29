package edu.yujie.socketex.base.finish.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.base.finish.ext.viewBinding
import edu.yujie.socketex.base.finish.inter.IRxJavaSubscribe
import edu.yujie.socketex.base.finish.util.CompositeDisposableLifecycleObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


open class BaseViewBindingDialogFragment : DialogFragment(), IRxJavaSubscribe {

    protected val binding by viewBinding<T>(viewBindingFactory)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}