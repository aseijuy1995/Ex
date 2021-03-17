package tw.north27.coachingapp.ui.fragment.item

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.databinding.FragmentNotifyMoreDialogBinding
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.notify.NotifyViewModel
import tw.north27.coachingapp.ui.fragment.main.NotifyFragmentArgs

class NotifyMoreDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentNotifyMoreDialogBinding>(FragmentNotifyMoreDialogBinding::inflate) {

    private val args: NotifyFragmentArgs by navArgs()

    private val viewModel by sharedViewModel<NotifyViewModel>()

    lateinit var notifyInfo: NotifyInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notifyInfo = args.notifyInfo
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            notifyInfo = this@NotifyMoreDialogFragment.notifyInfo
        }

        binding.ivDelete.clicks().subscribeWithRxLife {
            viewModel.deleteNotify(notifyInfo)
            findNavController().navigateUp()
        }

    }

}