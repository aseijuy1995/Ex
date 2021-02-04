package edu.yujie.socketex.finish.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.R
import edu.yujie.socketex.finish.inter.IRxJavaSubscribe
import edu.yujie.socketex.finish.util.DisposablesLifeObs
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(), IRxJavaSubscribe {

    protected val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    protected lateinit var navHostFrag: NavHostFragment

    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ToolbarBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //CompositeDisposable
        DisposablesLifeObs(viewLifecycleOwner, compositeDisposable)
        navHostFrag = requireActivity().supportFragmentManager.findFragmentById(R.id.frag_container_view) as NavHostFragment
        navController = navHostFrag.navController
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
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