package tw.north27.coachingapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import tw.north27.coachingapp.ext.dataBinding

open class BaseDataBindingFragment<T : ViewDataBinding>(private val layoutId: Int) : BaseFragment(layoutId) {

    protected val binding by dataBinding<T>(layoutId)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}