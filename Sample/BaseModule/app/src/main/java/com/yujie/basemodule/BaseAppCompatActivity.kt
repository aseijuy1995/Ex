package com.yujie.basemodule

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BaseAppCompatActivity<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : AppCompatActivity() {

		protected val binding by viewBinding(viewBindingFactory)

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				setContentView(binding.root)
		}
}