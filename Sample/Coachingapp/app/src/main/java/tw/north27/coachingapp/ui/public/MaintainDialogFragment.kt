package tw.north27.coachingapp.ui.public

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDialogFragment
import tw.north27.coachingapp.databinding.FragmentMaintainDialogBinding
import tw.north27.coachingapp.ext.viewBinding
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

        viewModel.appConfig.observe(viewLifecycleOwner) {
            val maintainInfo = it.maintainInfo!!

            binding.apply {
                ivPic.setImageResource(R.mipmap.ic_update_app_background)
                tvTime.text = maintainInfo.time
                tvDesc.text = maintainInfo.desc
            }
        }

        binding.btnClose.clicks().subscribeWithRxLife {
            act.finishAffinity()
        }
    }
}