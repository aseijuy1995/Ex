package tw.north27.coachingapp.ui2.fragment.global

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.yujie.basemodule.BaseDialogFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment : BaseDialogFragment<FragmentLoadingDialogBinding>(R.layout.fragment_loading_dialog) {

    companion object {
        private var dialog: DialogFragment? = null

        fun show(fragManager: FragmentManager) {
            if (dialog != null) dialog?.dismiss()
            dialog = LoadingDialogFragment()
            dialog?.show(fragManager, "LoadingDialogFragment")
        }

        fun dismiss() = dialog?.dismiss()
    }

    override val viewBindingFactory: (View) -> FragmentLoadingDialogBinding
        get() = FragmentLoadingDialogBinding::bind

}