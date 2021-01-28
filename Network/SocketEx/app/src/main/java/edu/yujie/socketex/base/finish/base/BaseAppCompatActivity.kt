package edu.yujie.socketex.base.finish.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.yujie.socketex.base.finish.util.CompositeDisposableLifecycleObserver
import edu.yujie.socketex.base.finish.inter.IBaseView
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseAppCompatActivity : AppCompatActivity(), IBaseView {

    override val TAG = javaClass.simpleName

    protected val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //CompositeDisposable
        CompositeDisposableLifecycleObserver(owner = this, compositeDisposable = compositeDisposable)
    }
}