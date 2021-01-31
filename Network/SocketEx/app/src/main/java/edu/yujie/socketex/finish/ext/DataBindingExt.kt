package edu.yujie.socketex.finish.ext

import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

fun <T : ViewDataBinding> AppCompatActivity.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView<T>(this, layoutId) }

fun <T : ViewDataBinding> Fragment.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.inflate<T>(layoutInflater, layoutId, view?.parent as ViewGroup?, false) }