package com.yujie.utilmodule.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yujie.utilmodule.ext.startDisposable
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

		protected val disposable = CompositeDisposable()

		protected lateinit var act: FragmentActivity

		protected lateinit var cxt: Context

		override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
				disposable.startDisposable(this)
				return super.onCreateView(inflater, container, savedInstanceState)
		}

		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
				super.onViewCreated(view, savedInstanceState)
				act = requireActivity()
				cxt = requireContext()
		}
}