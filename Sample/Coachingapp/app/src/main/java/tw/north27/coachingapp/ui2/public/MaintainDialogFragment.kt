package tw.north27.coachingapp.ui2.public

import android.os.Bundle
import android.view.View
import com.yujie.basemodule.BaseDialogFragment
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.observe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentMaintainDialogBinding
import tw.north27.coachingapp.ext2.clickThrottleFirst
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.viewModel.LaunchViewModel

class MaintainDialogFragment : BaseDialogFragment<FragmentMaintainDialogBinding>(R.layout.fragment_maintain_dialog) {

    override val viewBindingFactory: (View) -> FragmentMaintainDialogBinding
        get() = FragmentMaintainDialogBinding::bind

    private val viewModel by sharedViewModel<LaunchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
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
        }

        binding.btnClose.clickThrottleFirst().observe(viewLifecycleOwner) {
            act.finishAffinity()
        }
    }
}