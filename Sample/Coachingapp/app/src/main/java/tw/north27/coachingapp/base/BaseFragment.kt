package tw.north27.coachingapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.ext.startDisposablesLifeObs
import tw.north27.coachingapp.module.rx.IRxJavaSubscribe

open class BaseFragment(layoutId: Int) : Fragment(layoutId), IRxJavaSubscribe {

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

    var count = 0

    fun doubleClickToExit() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            count++
            if (count == 1)
                Toast.makeText(cxt, "再點選一次以退出!", Toast.LENGTH_SHORT).show()
            else if (count == 2)
                act.finishAffinity()

            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                count = 0
            }
        }
    }
}