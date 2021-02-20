package tw.north27.coachingapp.ext

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(crossinline inflater: (LayoutInflater) -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { inflater.invoke(layoutInflater) }

inline fun <T : ViewBinding> Fragment.viewBinding(crossinline inflater: (LayoutInflater) -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { inflater.invoke(layoutInflater) }




