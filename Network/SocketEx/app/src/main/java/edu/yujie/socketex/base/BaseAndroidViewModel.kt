package edu.yujie.socketex.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val TAG = javaClass.simpleName

    protected val context = application.applicationContext

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}