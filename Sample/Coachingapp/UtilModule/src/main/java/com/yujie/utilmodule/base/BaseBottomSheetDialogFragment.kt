package com.yujie.utilmodule.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yujie.utilmodule.base.ext.viewBinding

abstract class BaseBottomSheetDialogFragment<T : ViewBinding>(@LayoutRes private val layoutId: Int) : BottomSheetDialogFragment() {

		abstract val viewBind: (View) -> T

		protected val binding by viewBinding(viewBind)

		protected val cxt: Context
				get() = requireContext()

		protected val act: FragmentActivity
				get() = requireActivity()

		override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
				return inflater.inflate(layoutId, container, false)
		}

}