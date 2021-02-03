package edu.yujie.socketex.finish.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import edu.yujie.socketex.finish.ext.dataBinding

open class BaseDataBindingBottomSheetDialogFragment<T : ViewDataBinding>(layoutId: Int) : BaseBottomSheetDialogFragment() {

    protected val binding by dataBinding<T>(layoutId)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}