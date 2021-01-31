package edu.yujie.socketex.finish.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import edu.yujie.socketex.finish.ext.viewBinding

open class BaseViewBindingFragment<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : BaseFragment() {

    protected val binding by viewBinding<T>(viewBindingFactory)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}