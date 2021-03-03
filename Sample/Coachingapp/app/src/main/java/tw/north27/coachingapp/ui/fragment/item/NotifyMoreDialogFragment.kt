package tw.north27.coachingapp.ui.fragment.item

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.databinding.FragmentNotifyMoreDialogBinding
import tw.north27.coachingapp.module.base.fragment.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.viewModel.NotifyViewModel

class NotifyMoreDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentNotifyMoreDialogBinding>(FragmentNotifyMoreDialogBinding::inflate) {

    private val viewModel by sharedViewModel<NotifyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            notifyInfo = viewModel.notifyInfoMore.value
        }

        binding.ivDelete.clicks().subscribeWithRxLife {
//            viewModel.deleteNotifyInfo(viewModel.notifyInfoMore.value!!)
//            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setNotifyInfoToMorePage(null)
    }

}