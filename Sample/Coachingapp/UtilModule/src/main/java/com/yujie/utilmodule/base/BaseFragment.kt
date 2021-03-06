package com.yujie.utilmodule.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yujie.utilmodule.base.ext.viewBinding

abstract class BaseFragment<T : ViewBinding>(layoutId: Int) : Fragment(layoutId) {

    abstract val viewBind: (View) -> T

    protected val binding by viewBinding(viewBind)

    protected val cxt: Context
        get() = requireContext()

    protected val act: FragmentActivity
        get() = requireActivity()
}