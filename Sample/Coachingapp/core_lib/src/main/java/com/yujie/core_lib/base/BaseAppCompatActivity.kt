package com.yujie.core_lib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseAppCompatActivity<T : ViewBinding> : AppCompatActivity() {

    abstract val inflate: (LayoutInflater) -> T

    protected val binding by viewBinding { inflate.invoke(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

//    var count = 0
//
//    fun doubleClickToExit() {
//        onBackPressedDispatcher.addCallback {
//            count++
//            if (count == 1)
//                Toast.makeText(this@BaseAppCompatActivity, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show()
//            else if (count == 2)
//                finishAffinity()
//            lifecycleScope.launch(Dispatchers.IO) {
//                delay(1000)
//                count = 0
//            }
//        }
//    }
}