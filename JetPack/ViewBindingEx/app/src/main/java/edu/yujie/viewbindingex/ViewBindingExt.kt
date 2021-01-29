package edu.yujie.viewbindingex

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

//https://medium.com/@Zhuinden/simple-one-liner-viewbinding-in-fragments-and-activities-with-kotlin-961430c6c07c

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline inflater: (LayoutInflater) -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { inflater.invoke(layoutInflater) }

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline inflater: (LayoutInflater) -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { inflater.invoke(layoutInflater) }




