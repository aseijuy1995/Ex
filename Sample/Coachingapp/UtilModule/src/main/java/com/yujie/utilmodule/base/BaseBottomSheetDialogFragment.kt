package com.yujie.utilmodule.base

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected val cxt: Context
        get() = requireContext()

    protected val act: FragmentActivity
        get() = requireActivity()

}