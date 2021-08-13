package com.yujie.core_lib.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    protected val app = application

    protected val cxt: Context
        get() = app.applicationContext

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}