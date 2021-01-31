package edu.yujie.socketex.finish.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import edu.yujie.socketex.finish.ext.viewBinding

open class BaseViewBindingAppCompatActivity<T : ViewBinding>(inflater: (LayoutInflater) -> T) : BaseAppCompatActivity() {

    protected val binding by viewBinding(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
