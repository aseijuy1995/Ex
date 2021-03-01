package tw.north27.coachingapp.module.ext

import android.app.Activity
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * 已測試
 * */
fun <T : ViewDataBinding> Activity.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView<T>(this, layoutId) }

fun <T : ViewDataBinding> Fragment.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.inflate<T>(layoutInflater, layoutId, view?.parent as ViewGroup?, false) }

