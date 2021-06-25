package com.yujie.utilmodule.base.ext

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ViewBinding綁定（AppCompatActivity）
 * @param viewInflate >> ViewBinding::inflate
 * */
inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline viewInflate: (LayoutInflater) -> T): Lazy<T> {
		return lazy(LazyThreadSafetyMode.NONE) {
				viewInflate.invoke(layoutInflater)
		}
}

/**
 * ViewBinding綁定（Fragment）
 * @param viewBind >> ViewBinding::bind
 * */
fun <T : ViewBinding> Fragment.viewBinding(viewBind: (View) -> T): FragmentViewBindingProperty<T> {
		return FragmentViewBindingProperty(this, viewBind)
}

class FragmentViewBindingProperty<T>(
		private val fragment: Fragment,
		private val viewBind: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

		private var _binding: T? = null

		init {
				fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
						val viewLifeOwnerLiveDataObs = Observer<LifecycleOwner?> {
								val viewLifeOwner = it ?: return@Observer
								viewLifeOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
										override fun onDestroy(owner: LifecycleOwner) {
												_binding = null
										}
								})
						}

						override fun onCreate(owner: LifecycleOwner) {
								super.onCreate(owner)
								fragment.viewLifecycleOwnerLiveData.observeForever(viewLifeOwnerLiveDataObs)
						}

						override fun onDestroy(owner: LifecycleOwner) {
								super.onDestroy(owner)
								fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifeOwnerLiveDataObs)
						}
				})
		}

		override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
				_binding?.let { return it }

				if (!fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
						throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")

				return _binding ?: viewBind(thisRef.requireView()).apply {
						_binding = this
				}
		}
}