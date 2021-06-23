package tw.north27.coachingapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.yujie.pushmodule.fcm.FirebaseMsg
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.alertGoogleService
import com.yujie.utilmodule.util.UpdateApp
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.ext.updateApp
import tw.north27.coachingapp.model.AppCode
import tw.north27.coachingapp.model.SignInCode
import tw.north27.coachingapp.ui.launch2.Launch2Activity
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override val viewBind: (View) -> FragmentStartBinding
        get() = FragmentStartBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.appConfigState.observe(viewLifecycleOwner) {
            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    when (appConfig.appCode) {
                        AppCode.MAINTAIN.code -> {
                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
                        }
                        AppCode.RUN.code -> {
                            val updateInfo = appConfig.runInfo!!
                            act.updateApp(updateInfo.versionName).builder {
                                versionNameMode = UpdateApp.VersionNameMode.DEFAULT
                            }.execute(
                                newVersion = { _, _ -> findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) },
                                noNewVersion = { checkGoogleServiceAndSignIn() }
                            )
                        }
                    }
                }
            }
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Empty -> {
                    findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
                }
                is ViewState.Data -> {
                    val signIn = it.data
                    Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        delay(200)
                        when (signIn.signInCode) {
                            SignInCode.SIGN_IN_SUC.code -> {
                                startActivity(Intent(act, Launch2Activity::class.java))
                                act.finish()
                            }
                            SignInCode.SIGN_IN_FAIL.code -> {
                                findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
                            }
                        }

                    }
                }
            }
        }

        setFragmentResultListener(UpdateDialogFragment.REQUEST_KEY_UPDATE_CLOSE) { _, bundle ->
            bundle.getBoolean(UpdateDialogFragment.KEY_UPDATE_CLOSE).takeIf { it }?.let {
                checkGoogleServiceAndSignIn()
            }
        }

        viewModel.getAppConfig()
    }

    private fun checkGoogleServiceAndSignIn() {
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
            .addOnSuccessListener {
                FirebaseMsg.getInstance(complete = {
                    viewModel.checkSignIn()
                })
            }.addOnFailureListener {
                act.alertGoogleService()
            }
    }
}