package com.yujie.utilmodule.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yujie.utilmodule.R
import com.yujie.utilmodule.base.ext.viewBinding

abstract class BaseDialogFragment<T : ViewBinding>(layoutId: Int) : DialogFragment(layoutId) {

    abstract val viewBindingFactory: (View) -> T

    protected val binding by viewBinding(viewBindingFactory)

    protected lateinit var act: FragmentActivity

    protected lateinit var cxt: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Transparent)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        act = requireActivity()
        cxt = requireContext()
    }
}