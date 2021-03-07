package tw.north27.coachingapp.ext

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
 * 已測試
 * */
fun <T : ViewDataBinding> Activity.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView<T>(this, layoutId) }

fun <T : ViewDataBinding> Fragment.dataBinding(layoutId: Int) =
    lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.inflate<T>(layoutInflater, layoutId, view?.parent as ViewGroup?, false) }

fun <T : ViewDataBinding, VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.dataBinding(
    dataBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T,
    inflate: LayoutInflater,
    viewGroup: ViewGroup,
    attachToParent: Boolean
) = lazy(LazyThreadSafetyMode.NONE) { dataBindingFactory.invoke(inflate, viewGroup, attachToParent) }