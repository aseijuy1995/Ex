package edu.yujie.viewbindingext

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import edu.yujie.databindingext.extension.ViewBindingProperty

/**
 * @author YuJie on 2020/10/17
 * @describe
 * private val binding: FragmentMain2Binding by viewBinding {
 *      FragmentMain2Binding.bind(requireView())
 * }
 */
fun <T : ViewBinding> Fragment.viewBinding(binder: () -> T): ViewBindingProperty<Fragment, T> =
    object : ViewBindingProperty<Fragment, T>(this, binder) {
        override val lifecycleOwner: LifecycleOwner
            get() = viewLifecycleOwner
    }

