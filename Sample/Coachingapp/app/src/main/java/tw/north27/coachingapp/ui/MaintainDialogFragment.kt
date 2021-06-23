package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentMaintainDialogBinding
import tw.north27.coachingapp.model.AppCode
import tw.north27.coachingapp.viewModel.StartViewModel
import java.text.SimpleDateFormat

class MaintainDialogFragment : BaseDialogFragment<FragmentMaintainDialogBinding>(R.layout.fragment_maintain_dialog) {

    override val viewBind: (View) -> FragmentMaintainDialogBinding
        get() = FragmentMaintainDialogBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.appConfigState.observe(viewLifecycleOwner) {
            if (it is ViewState.Data) {
                val appConfig = it.data
                when (appConfig.appCode) {
                    AppCode.MAINTAIN.code -> {
                        val maintainInfo = appConfig.maintainInfo!!
                        binding.tvTime.isVisible = (maintainInfo.time != null)
                        binding.tvContent.isVisible = (maintainInfo.content != null) && maintainInfo.content.isNotEmpty()
                        binding.apply {
                            tvTime.text = String.format(
                                "%s\n%s", getString(R.string.expected_complete_time), try {
                                    SimpleDateFormat("yyyy-MM-dd HH:mm").format(maintainInfo.time)
                                } catch (e: Exception) {
                                    ""
                                }
                            )
                            tvContent.text = maintainInfo.content
                        }
                    }
                }
            }
        }

        binding.btnClose.clicksObserve(owner = viewLifecycleOwner) {
            act.finishAffinity()
        }
    }
}