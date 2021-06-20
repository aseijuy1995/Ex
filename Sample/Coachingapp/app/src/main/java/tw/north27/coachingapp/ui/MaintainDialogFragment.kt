package tw.north27.coachingapp.ui//package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentMaintainDialogBinding
import tw.north27.coachingapp.model.AppState
import tw.north27.coachingapp.viewModel.StartViewModel

class MaintainDialogFragment : BaseDialogFragment<FragmentMaintainDialogBinding>(R.layout.fragment_maintain_dialog) {

    override val viewBind: (View) -> FragmentMaintainDialogBinding
        get() = FragmentMaintainDialogBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.appConfigState.observe(viewLifecycleOwner) {
            if (it is ViewState.Data) {
                val appConfig = it.data
                if (appConfig.appState == AppState.MAINTAIN) {
                    val maintainInfo = appConfig.maintainInfo!!
                    binding.apply {
                        tvTitle.text = maintainInfo.title
                        tvTime.text = String.format("%s:\n%s", getString(R.string.expected_complete_time), maintainInfo.time)
                        tvText.text = maintainInfo.text
                    }
                }
            }
        }

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            act.finishAffinity()
        }
    }
}