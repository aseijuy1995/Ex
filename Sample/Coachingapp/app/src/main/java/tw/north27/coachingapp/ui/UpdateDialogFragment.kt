package tw.north27.coachingapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentUpdateDialogBinding
import tw.north27.coachingapp.model.AppCode
import tw.north27.coachingapp.viewModel.StartViewModel

class UpdateDialogFragment : BaseDialogFragment<FragmentUpdateDialogBinding>(R.layout.fragment_update_dialog) {

    override val viewBind: (View) -> FragmentUpdateDialogBinding
        get() = FragmentUpdateDialogBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

    companion object {
        const val REQUEST_KEY_UPDATE_CLOSE = "REQUEST_KEY_UPDATE_CLOSE"

        const val KEY_UPDATE_CLOSE = "KEY_UPDATE_CLOSE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    when (appConfig.appCode) {
                        AppCode.RUN.code -> {
                            val updateInfo = appConfig.runInfo!!
                            binding.tvSize.isVisible = (updateInfo.size != null) && updateInfo.size.isNotEmpty()
                            binding.tvContent.isVisible = (updateInfo.content != null) && updateInfo.content.isNotEmpty()
                            binding.llcClose.isVisible = !updateInfo.isCompulsory
                            binding.apply {
                                tvTitle.text = String.format("%s(%s)", getString(R.string.update_title), updateInfo.versionName)
                                tvSize.text = String.format("%s: %s", getString(R.string.update_size), updateInfo.size)
                                tvContent.text = updateInfo.content
                            }
                            binding.btnUpdate.clicksObserve(owner = viewLifecycleOwner) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.url)).apply {
                                    flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                                }
                                cxt.startActivity(intent)
                            }
                            binding.llcClose.clicksObserve(owner = viewLifecycleOwner) {
                                setFragmentResult(
                                    REQUEST_KEY_UPDATE_CLOSE,
                                    bundleOf(KEY_UPDATE_CLOSE to true)
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