package tw.north27.coachingapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.yujie.basemodule.BaseDialogFragment
import com.yujie.utilmodule.ViewState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentUpdateDialogBinding
import tw.north27.coachingapp.ext2.clicksObserve
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.viewModel.StartViewModel

class UpdateDialogFragment : BaseDialogFragment<FragmentUpdateDialogBinding>(R.layout.fragment_update_dialog) {

    override val viewBindingFactory: (View) -> FragmentUpdateDialogBinding
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
                    if (appConfig.appState == AppState.RUN) {
                        val updateInfo = appConfig.updateInfo!!
                        binding.apply {
                            tvTitle.text = String.format(getString(R.string.update_title), updateInfo.versionName)
                            tvSize.text = String.format("%s: %s", getString(R.string.update_size), updateInfo.size)
                            tvText.text = updateInfo.text
                        }

                        binding.btnUpdate.clicksObserve(owner = viewLifecycleOwner) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateInfo.url)).apply {
                                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                            }
                            cxt.startActivity(intent)
                        }

                        binding.tvText.isVisible = updateInfo.text.isNotEmpty()
                        binding.tvSize.isVisible = updateInfo.size.isNotEmpty()
                        binding.llcClose.isVisible = !updateInfo.isMandatory

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