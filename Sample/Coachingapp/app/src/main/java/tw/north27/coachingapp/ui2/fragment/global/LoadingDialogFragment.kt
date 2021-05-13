package tw.north27.coachingapp.ui2.fragment.global

import android.os.Bundle
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDialogFragment
import tw.north27.coachingapp.databinding.FragmentLoadingDialogBinding
import tw.north27.coachingapp.ext2.viewBinding

class LoadingDialogFragment : BaseDialogFragment(R.layout.fragment_loading_dialog) {

    private val binding by viewBinding(FragmentLoadingDialogBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialogTheme)
        isCancelable = false
    }

}