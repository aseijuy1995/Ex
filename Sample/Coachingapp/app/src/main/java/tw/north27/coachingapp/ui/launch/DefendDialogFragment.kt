package tw.north27.coachingapp.ui.launch

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentDefendDialogBinding
import tw.north27.coachingapp.model.response.AppCode
import tw.north27.coachingapp.viewModel.PublicViewModel
import java.text.SimpleDateFormat

class DefendDialogFragment : BaseDialogFragment<FragmentDefendDialogBinding>(R.layout.fragment_defend_dialog) {

    override val viewBind: (View) -> FragmentDefendDialogBinding
        get() = FragmentDefendDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        publicVM.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    if (appConfig.appCode == AppCode.DEFEND.code) {
                        val defendInfo = appConfig.defendInfo!!
                        if (defendInfo.bgUrl.isNotEmpty()) binding.ivBg.bindImg(url = defendInfo.bgUrl)
                        if (defendInfo.title.isNotEmpty()) binding.tvTitle.text = defendInfo.title
                        if (defendInfo.content.isNotEmpty()) binding.tvContent.text = defendInfo.content
                        binding.apply {
                            tvTime.apply {
                                isVisible = (defendInfo.time != null)
                                defendInfo.time?.let {
                                    text = SimpleDateFormat("yyyy/MM/dd HH:mm").format(defendInfo.time)
                                }
                            }
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