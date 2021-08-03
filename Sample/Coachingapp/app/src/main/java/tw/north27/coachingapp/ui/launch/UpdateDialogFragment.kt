package tw.north27.coachingapp.ui.launch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.yujie.core_lib.adapter.bindImg
import com.yujie.core_lib.base.BaseDialogFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentUpdateDialogBinding
import tw.north27.coachingapp.model.response.AppCode
import tw.north27.coachingapp.viewModel.PublicViewModel

class UpdateDialogFragment : BaseDialogFragment<FragmentUpdateDialogBinding>(R.layout.fragment_update_dialog) {

    override val viewBind: (View) -> FragmentUpdateDialogBinding
        get() = FragmentUpdateDialogBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    companion object {
        const val REQUEST_KEY_UPDATE = "REQUEST_KEY_UPDATE"

        const val KEY_UPDATE = "KEY_UPDATE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        publicVM.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    if (appConfig.appCode == AppCode.MOTION.code) {
                        appConfig.motionInfo?.let { updateInfo ->
                            if (updateInfo.bgUrl.isNotEmpty()) binding.ivBg.bindImg(url = updateInfo.bgUrl)
                            if (updateInfo.title.isNotEmpty()) binding.tvTitle.text = updateInfo.title
                            if (updateInfo.content.isNotEmpty()) binding.tvContent.text = updateInfo.content

                            binding.apply {
                                tvTitle.append("\t(${updateInfo.versionName})")
                                tvSize.apply {
                                    isVisible = updateInfo.size.isNotEmpty()
                                    text = String.format("%s: %s", getString(R.string.update_size), updateInfo.size)
                                }

                                btnUpdate.clicksObserve(owner = viewLifecycleOwner) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.url)).apply {
                                        flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                    }
                                    cxt.startActivity(intent)
                                }

                                llcClose.apply {
                                    isVisible = !updateInfo.isCompulsory
                                    clicksObserve(owner = viewLifecycleOwner) {
                                        setFragmentResult(
                                            REQUEST_KEY_UPDATE,
                                            bundleOf(KEY_UPDATE to true)
                                        )
                                        findNavController().navigateUp()
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}