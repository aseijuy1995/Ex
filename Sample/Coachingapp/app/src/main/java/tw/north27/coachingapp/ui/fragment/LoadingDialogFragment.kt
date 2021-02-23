package tw.north27.coachingapp.ui.fragment

import android.os.Bundle
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseViewBindingDialogFragment
import tw.north27.coachingapp.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment : BaseViewBindingDialogFragment<FragmentLoadingDialogBinding>(FragmentLoadingDialogBinding::inflate) {

    companion object {
        val TAG = "LoadingDialogFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialogTheme)
        isCancelable = false
    }

//    private var dialogView: View? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        if (dialogView == null) {
//            dialogView = super.onCreateView(inflater, container, savedInstanceState)!!
//        }
//        return dialogView
//    }
//
//    override fun dismiss() {
//        super.dismiss()
//        dialogView = null
//    }
}