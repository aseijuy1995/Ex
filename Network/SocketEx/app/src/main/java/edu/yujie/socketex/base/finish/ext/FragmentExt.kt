package edu.yujie.socketex.base.finish.ext

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline inflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        inflater.invoke(layoutInflater)
    }