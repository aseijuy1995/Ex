package tw.north27.coachingapp.ui.public

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDialogFragment
import tw.north27.coachingapp.databinding.FragmentMaintainDialogBinding
import tw.north27.coachingapp.ext2.clickThrottleFirst
import tw.north27.coachingapp.ext2.viewBinding
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.util2.ViewState
import tw.north27.coachingapp.viewModel.StartViewModel

class MaintainDialogFragment : BaseDialogFragment(R.layout.fragment_maintain_dialog) {

    private val binding by viewBinding(FragmentMaintainDialogBinding::bind)

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MaintainDialogTheme)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
                }
                is ViewState.Data -> {
                    val appConfig = it.data

                    if (appConfig.appState == AppState.MAINTAIN) {
                        val maintainInfo = appConfig.maintainInfo!!
                        binding.apply {
                            tvTime.text = maintainInfo.time
                            tvDesc.text = maintainInfo.desc
                        }
                    }
                }
                is ViewState.Error -> {

                }
                is ViewState.Network -> {

                }
            }
        }

        binding.btnClose.clickThrottleFirst().subscribeWithRxLife {
            act.finishAffinity()
        }
    }
}