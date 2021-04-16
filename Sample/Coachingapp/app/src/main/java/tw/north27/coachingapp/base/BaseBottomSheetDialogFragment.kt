package tw.north27.coachingapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import tw.north27.coachingapp.ext.startDisposablesLifeObs
import tw.north27.coachingapp.module.rx.IRxJavaSubscribe


open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        compositeDisposable.startDisposablesLifeObs(this)
        return super.onCreateView(inflater, container, savedInstanceState)
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

//    private val loadingDialogNavDirections = NavGraphDirections.actionToFragmentLoadingDialog()

    fun showLoadingDialog() {
//        findNavController().navigate(loadingDialogNavDirections)
    }

    fun dismissLoadingDialog() {
//        findNavController().navigateUp()
    }

}