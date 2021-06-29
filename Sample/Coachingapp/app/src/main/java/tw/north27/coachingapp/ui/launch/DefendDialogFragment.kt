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
                        if (defendInfo.bgUrl.isNotEmpty()) binding.ivBg.bindImg(url = defendInfo.bgUrl)
                        if (defendInfo.title.isNotEmpty()) binding.tvTitle.text = defendInfo.title
                        binding.apply {
                            tvTime.apply {
                                isVisible = (defendInfo.time != null)
                                defendInfo.time?.let {
                                    //FIXME 時間格式統一修改
                                    text = SimpleDateFormat("yyyy/MM/dd HH:mm").format(defendInfo.time)
                                }
                            }
                            tvContent.apply {
                                isVisible = defendInfo.content.isNotEmpty()
                                text = defendInfo.content
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