package edu.yujie.socketex.ui

import android.os.Bundle
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragLoadingDialogBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingDialogFragment

class LoadingDialogFragment : BaseViewBindingDialogFragment<FragLoadingDialogBinding>(FragLoadingDialogBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialogTheme)
        isCancelable = false
    }
}