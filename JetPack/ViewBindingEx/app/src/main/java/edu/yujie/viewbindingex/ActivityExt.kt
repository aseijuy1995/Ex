package edu.yujie.viewbindingex

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

/**
 * @author YuJie on 2020/10/17
 * @describe
 * private val binding by viewBinding {
 *      ActivityMain2Binding.inflate(layoutInflater).apply {
 *      setContentView(root)
 *      }
 * }
 */
fun <T : ViewBinding> AppCompatActivity.viewBinding(binder: () -> T): ViewBindingProperty<AppCompatActivity, T> =
    object : ViewBindingProperty<AppCompatActivity, T>(this, binder) {
        override val lifecycleOwner: LifecycleOwner
            get() = thisRef
    }