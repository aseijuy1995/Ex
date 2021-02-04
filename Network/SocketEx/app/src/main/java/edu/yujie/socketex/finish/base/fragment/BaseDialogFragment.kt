package edu.yujie.socketex.finish.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.R
import edu.yujie.socketex.finish.inter.IRxJavaSubscribe
import edu.yujie.socketex.finish.util.DisposablesLifeObs
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


open class BaseDialogFragment : DialogFragment(), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected lateinit var act: FragmentActivity

    protected lateinit var cxt: Context

    protected val compositeDisposable = CompositeDisposable()

    protected lateinit var navHostFrag: NavHostFragment

    protected lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        act = requireActivity()
        cxt = requireContext()
        //CompositeDisposable
        DisposablesLifeObs(viewLifecycleOwner, compositeDisposable)
        navHostFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navHostFrag.navController
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun <T> Observable<T>.subscribeWithLife(): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe()

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext)

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext, onError)

    override fun <T> Observable<T>.subscribeWithLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext, onError, onComplete)
}