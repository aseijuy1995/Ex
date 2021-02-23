package tw.north27.coachingapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.view.BaseViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.ext.autoBreatheAlphaAnim
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.util.http.UpdateAppHttpUtil
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseViewBindingFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private val viewModel by viewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivIcon.autoBreatheAlphaAnim(viewLifecycleOwner, compositeDisposable)

        lifecycleScope.launch {
            delay(1500L)
            showLoadingDialog()

            viewModel.getVersion().observe(viewLifecycleOwner) {
                dismissLoadingDialog()

                binding.tvVersion.text = it.version

                requireActivity().updateApp(it.apkDownloadUrl, UpdateAppHttpUtil(requireContext(), it)) {
                    topPic = R.mipmap.ic_version_pic
                    setUpdateDialogFragmentListener {
                        viewModel.checkSignIn()
                    }
                }.check {
                    onAfter { dismissLoadingDialog() }
                    noNewApp { viewModel.checkSignIn() }
                }
            }
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

        viewModel.signIn.observe(viewLifecycleOwner, ::onSignObs)

    }

    private fun onToastObs(pair: Pair<StartViewModel.ToastType, String>) {
        when (pair.first) {
            StartViewModel.ToastType.VERSION -> {
                dismissLoadingDialog()
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
            StartViewModel.ToastType.CHECK_SIGN_IN -> {
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSignObs(result: SignInResult) {
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
        }
    }
}