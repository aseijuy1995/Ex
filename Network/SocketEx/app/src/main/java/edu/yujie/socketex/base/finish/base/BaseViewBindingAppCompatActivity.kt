package edu.yujie.socketex.base.finish.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import edu.yujie.socketex.base.finish.ext.viewBinding

open class BaseViewBindingAppCompatActivity<T : ViewBinding>(private val inflater: (LayoutInflater) -> T) : BaseAppCompatActivity() {

    protected val binding by viewBinding(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override val layoutId: Int = TODO()
}
