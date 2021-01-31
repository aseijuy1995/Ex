package edu.yujie.socketex.finish.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import edu.yujie.socketex.finish.ext.dataBinding


open class BaseDataBindingDialogFragment<T : ViewDataBinding>(layoutId: Int) : BaseDialogFragment() {

    protected val binding by dataBinding<T>(layoutId)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}