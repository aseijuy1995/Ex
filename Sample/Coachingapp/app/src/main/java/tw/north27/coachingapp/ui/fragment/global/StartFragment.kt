package tw.north27.coachingapp.ui.fragment.global

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseCoachingViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.module.ext.autoBreatheAlphaAnim
import tw.north27.coachingapp.util.http.UpdateHttpUtil
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseCoachingViewBindingFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivIcon.autoBreatheAlphaAnim(viewLifecycleOwner, compositeDisposable)
        binding.tvVersion.text = String.format("v:${BuildConfig.VERSION_NAME}")
        showLoadingDialog()

        viewModel.getAppConfigInfo().observe(viewLifecycleOwner) {
            dismissLoadingDialog()
            when (it.appState) {
                AppState.MAINTAIN -> {
                    findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
                }
                AppState.RUN -> {
                    it.updateInfo?.let {
                        act.updateApp(it.url, UpdateHttpUtil(cxt, it)) {
                            topPic = R.mipmap.ic_version_pic
                            setUpdateDialogFragmentListener {
                                viewModel.checkSignIn()
                            }
                        }.check {
                            noNewApp {
                                viewModel.checkSignIn()
                            }
                            onAfter{
                                
                            }
                        }
                    }
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

    private fun onSignObs(info: SignInInfo) {
        lifecycleScope.launch {
            delay(1000)
            findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
        }
    }
}