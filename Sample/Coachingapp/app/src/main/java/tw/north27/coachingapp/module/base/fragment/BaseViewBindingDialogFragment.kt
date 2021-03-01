package tw.north27.coachingapp.module.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import tw.north27.coachingapp.module.ext.viewBinding

open class BaseViewBindingDialogFragment<T : ViewBinding>(viewBindingFactory: (LayoutInflater) -> T) : BaseDialogFragment() {

    protected val binding by viewBinding<T>(viewBindingFactory)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}