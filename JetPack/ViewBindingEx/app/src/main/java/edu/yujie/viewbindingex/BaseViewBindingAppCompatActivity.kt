package edu.yujie.viewbindingex

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseViewBindingAppCompatActivity<T : ViewBinding>(inflater: (LayoutInflater) -> T) : AppCompatActivity() {

    protected val binding by viewBinding(inflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
