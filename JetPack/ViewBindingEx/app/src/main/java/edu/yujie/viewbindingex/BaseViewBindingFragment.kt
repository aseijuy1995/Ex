package edu.yujie.socketex.base.finish.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import edu.yujie.viewbindingex.viewBinding

open class BaseViewBindingFragment<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : Fragment() {

    protected val binding by viewBinding<T>(viewBindingFactory)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}