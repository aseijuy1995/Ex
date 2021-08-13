package tw.north27.coachingapp.ui.launch2.share

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.yujie.core_lib.base.BaseDialogFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment : BaseDialogFragment<FragmentLoadingDialogBinding>(R.layout.fragment_loading_dialog) {

    override val bind: (View) -> FragmentLoadingDialogBinding
        get() = FragmentLoadingDialogBinding::bind

    companion object {
        private var dialog: DialogFragment? = null

        fun show(fragManager: FragmentManager) {
            println("dialog != null = ${dialog != null}")
            if (dialog != null) dialog?.dismiss()
            dialog = LoadingDialogFragment()
            dialog?.showNow(fragManager, "tag")
        }

        fun dismiss() = dialog?.dismiss()
    }

}