package tw.north27.coachingapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.yujie.basemodule.viewBinding

open class BaseViewBindingBottomSheetDialogFragment<T : ViewBinding>(viewBindingFactory: (View) -> T) : BaseBottomSheetDialogFragment() {

    protected val binding by viewBinding<T>(viewBindingFactory)

    protected lateinit var act: FragmentActivity

    protected lateinit var cxt: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        act = requireActivity()
        cxt = requireContext()
    }
}