package com.yujie.utilmodule.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.yujie.utilmodule.base.ext.viewBinding

open class BaseAppCompatActivity<T : ViewBinding>(viewInflate: (LayoutInflater) -> T) : AppCompatActivity() {

    protected val binding by viewBinding(viewInflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}