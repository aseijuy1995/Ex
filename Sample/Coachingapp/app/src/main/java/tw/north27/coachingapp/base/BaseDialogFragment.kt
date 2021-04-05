package tw.north27.coachingapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.ext.startDisposablesLifeObs
import tw.north27.coachingapp.module.rx.IRxJavaSubscribe

open class BaseDialogFragment(layoutId: Int) : DialogFragment(layoutId), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected lateinit var act: FragmentActivity

    protected lateinit var cxt: Context

    protected val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.startDisposablesLifeObs(viewLifecycleOwner)
        act = requireActivity()
        cxt = requireContext()
    }

    override fun <T> Observable<T>.subscribeWithRxLife(): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe()

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext)

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext, onError)

    override fun <T> Observable<T>.subscribeWithRxLife(onNext: (T) -> Unit, onError: (Throwable) -> Unit, onComplete: () -> Unit): Disposable? =
        bindToLifecycle(viewLifecycleOwner)
            .subscribe(onNext, onError, onComplete)

    private val loadingDialogNavDirections = NavGraphDirections.actionToFragmentLoadingDialog()

    fun showLoadingDialog() {
        findNavController().navigate(loadingDialogNavDirections)
    }

    fun dismissLoadingDialog() {
        findNavController().navigateUp()
    }
}