package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseBottomSheetDialogFragment<T : ViewDataBinding> : BottomSheetDialogFragment() {

    lateinit var binding: T

    abstract val layoutId: Int

    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<T>(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }

}