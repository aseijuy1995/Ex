package com.yujie.basemodule

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yujie.basemodule.rxjava.IRxObserve

abstract class BaseFragment<T : ViewBinding>(layoutId: Int) : Fragment(layoutId), IRxObserve {

		abstract val viewBindingFactory: (View) -> T

		protected val binding by viewBinding(viewBindingFactory)

		protected lateinit var act: FragmentActivity

		protected lateinit var cxt: Context

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)
				act = requireActivity()
				cxt = requireContext()
		}
}