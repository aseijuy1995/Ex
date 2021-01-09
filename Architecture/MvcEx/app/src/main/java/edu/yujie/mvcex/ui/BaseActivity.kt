package edu.yujie.mvcex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>() : AppCompatActivity() {

    val TAG = javaClass.simpleName

    lateinit var binding: T

    abstract val layoutId: Int

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<T>(this, layoutId)
    }

}