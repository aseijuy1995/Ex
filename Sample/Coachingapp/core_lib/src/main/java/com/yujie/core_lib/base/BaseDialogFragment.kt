package com.yujie.core_lib.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yujie.core_lib.R

abstract class BaseDialogFragment<T : ViewBinding>(layoutId: Int) : DialogFragment(layoutId) {

    abstract val bind: (View) -> T

    protected val binding by viewBinding { bind.invoke(it) }

    protected val cxt: Context
        get() = requireContext()

    protected val act: FragmentActivity
        get() = requireActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Transparent)
        isCancelable = false
    }
}