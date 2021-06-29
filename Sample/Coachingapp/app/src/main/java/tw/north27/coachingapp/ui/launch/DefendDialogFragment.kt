package tw.north27.coachingapp.ui.launch

import android.os.Bundle
import android.view.View
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentDefendDialogBinding
import tw.north27.coachingapp.model.AppCode
import tw.north27.coachingapp.viewModel.StartViewModel
import java.text.SimpleDateFormat

class DefendDialogFragment : BaseDialogFragment<FragmentDefendDialogBinding>(R.layout.fragment_defend_dialog) {

    override val viewBind: (View) -> FragmentDefendDialogBinding
        get() = FragmentDefendDialogBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.appConfigState.observe(viewLifecycleOwner) {
            if (it is ViewState.Data) {
                val appConfig = it.data
                when (appConfig.appCode) {
                    AppCode.DEFEND.code -> {
                        val defendInfo = appConfig.defendInfo!!
                        if (defendInfo.title != null && defendInfo.title.isNotEmpty() && defendInfo.title.isNotBlank())
                            binding.title.text = defendInfo.title

                        binding.tvTime.isVisible = (defendInfo.time != null)
                        binding.tvContent.isVisible = (defendInfo.content != null) && defendInfo.content.isNotEmpty()
                        binding.apply {
                            tvTime.text = String.format(
                                "%s\n%s", getString(R.string.expected_complete_time), try {
                                    SimpleDateFormat("yyyy-MM-dd HH:mm").format(defendInfo.time)
                                } catch (e: Exception) {
                                    ""
                                }
                            )
                            tvContent.text = defendInfo.content
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