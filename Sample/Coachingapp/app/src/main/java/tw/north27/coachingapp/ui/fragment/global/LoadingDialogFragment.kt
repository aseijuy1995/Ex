package tw.north27.coachingapp.ui.fragment.global

import android.os.Bundle
import tw.north27.coachingapp.R
import tw.north27.coachingapp.module.base.fragment.BaseViewBindingDialogFragment
import tw.north27.coachingapp.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment : BaseViewBindingDialogFragment<FragmentLoadingDialogBinding>(FragmentLoadingDialogBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialogTheme)
        isCancelable = false
    }

}