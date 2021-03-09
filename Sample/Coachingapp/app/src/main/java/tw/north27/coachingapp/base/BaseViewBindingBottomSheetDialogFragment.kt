package tw.north27.coachingapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import tw.north27.coachingapp.ext.viewBinding


open class BaseViewBindingBottomSheetDialogFragment<T : ViewBinding>(inflater: (LayoutInflater) -> T) : BaseBottomSheetDialogFragment() {

    protected val binding by viewBinding<T>(inflater)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }
}