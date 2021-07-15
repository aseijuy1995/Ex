package com.yujie.utilmodule.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.yujie.utilmodule.R
import com.yujie.utilmodule.base.ext.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseAppCompatActivity<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : AppCompatActivity() {

		protected val binding by viewBinding(viewBindingFactory)

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				setContentView(binding.root)
		}

		var count = 0

		protected fun doubleClickToExit() {
				onBackPressedDispatcher.addCallback {
						count++
						if (count == 1)
								Toast.makeText(this@BaseAppCompatActivity, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show()
						else if (count == 2)
								finishAffinity()
						lifecycleScope.launch(Dispatchers.IO) {
								delay(1000)
								count = 0
						}
				}
		}
}