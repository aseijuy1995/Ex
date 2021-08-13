package com.yujie.core_lib.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(layoutId: Int) : Fragment(layoutId) {

    abstract val bind: (View) -> T

    protected val binding by viewBinding { bind.invoke(it) }

    protected val cxt: Context
        get() = requireContext()

    protected val act: FragmentActivity
        get() = requireActivity()
}