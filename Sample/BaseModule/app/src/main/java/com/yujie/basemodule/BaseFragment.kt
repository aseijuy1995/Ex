package com.yujie.basemodule

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding

open class BaseFragment<T : ViewBinding>(layoutId: Int, viewBindingFactory: (View) -> T) : Fragment(layoutId) {

		protected val binding by viewBinding(viewBindingFactory)

		protected lateinit var act: FragmentActivity

		protected lateinit var cxt: Context

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)
				act = requireActivity()
				cxt = requireContext()
		}
}