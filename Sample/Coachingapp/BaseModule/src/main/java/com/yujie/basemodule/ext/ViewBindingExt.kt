package com.yujie.basemodule

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

//fun <T : ViewBinding> AppCompatActivity.viewBinding(viewBindingFactory: (LayoutInflater) -> T): ActivityViewBindingProperty<T> = ActivityViewBindingProperty(this, viewBindingFactory)
//
//class ActivityViewBindingProperty<T>(private val appCompatActivity: AppCompatActivity, private val viewBindingFactory: (LayoutInflater) -> T) : ReadOnlyProperty<AppCompatActivity, T> {
//		private var _binding: T? = null
//
//		init {
//				appCompatActivity.lifecycle.addObserver(object : DefaultLifecycleObserver {
//						override fun onDestroy(owner: LifecycleOwner) {
//								super.onDestroy(owner)
//								_binding = null
//						}
//				})
//		}
//
//		override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
//				val appCompatActivityLifecycle = appCompatActivity.lifecycle
//				if (!appCompatActivityLifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
//						throw IllegalStateException("Should not attempt to get bindings when AppCompatActivity views are destroyed.")
//				}
//				return _binding
//						?: viewBindingFactory(thisRef.layoutInflater).apply {
//								_binding = this
//						}
//		}
//}

/**
 * AppCompatActivity
 * */
fun <T : ViewBinding> AppCompatActivity.viewBinding(viewBindingFactory: (LayoutInflater) -> T): Lazy<T> {
		return lazy(LazyThreadSafetyMode.NONE) { viewBindingFactory.invoke(layoutInflater) }
}

/**
 * Fragment
 * */
fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T): FragmentViewBindingProperty<T> = FragmentViewBindingProperty(this, viewBindingFactory)

class FragmentViewBindingProperty<T>(private val fragment: Fragment, private val viewBindingFactory: (View) -> T) : ReadOnlyProperty<Fragment, T> {
		private var _binding: T? = null

		init {
				fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

						override fun onDestroy(owner: LifecycleOwner) {
								super.onDestroy(owner)
								_binding = null
						}
				})
		}

		override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
				val fragmentLifecycle = fragment.viewLifecycleOwner.lifecycle
				if (!fragmentLifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
						throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
				}
				return _binding
						?: viewBindingFactory(thisRef.requireView()).apply {
								_binding = this
						}
		}
}