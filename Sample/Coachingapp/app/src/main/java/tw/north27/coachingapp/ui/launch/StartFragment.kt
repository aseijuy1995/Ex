package tw.north27.coachingapp.ui.launch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.alertGoogleService
import com.yujie.core_lib.util.ViewState
import com.yujie.pushmodule.fcm.FirebaseMsg
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphLaunchDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.ext.updateApp
import tw.north27.coachingapp.model.response.AppCode
import tw.north27.coachingapp.model.response.SignCode
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity
import tw.north27.coachingapp.viewModel.PublicViewModel
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override val bind: (View) -> FragmentStartBinding
        get() = FragmentStartBinding::bind

    private val publicVM by sharedViewModel<PublicViewModel>()

    private val viewModel by viewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        publicVM.appConfigState.observe(viewLifecycleOwner) {
            binding.itemIcon.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    when (appConfig.appCode) {
                        AppCode.MOTION.code -> {
                            appConfig.motionInfo?.let { motionInfo ->
                                act.updateApp(motionInfo.versionName).builder { versionNameMode = motionInfo.versionNameMode }.execute(
                                    newVersion = { _, _ ->
                                        findNavController().navigate(NavGraphLaunchDirections.actionToFragmentUpdateDialog())
                                    },
                                    noNewVersion = { checkGoogleServiceAndSignIn() }
                                )
                            }
                        }
                        AppCode.DEFEND.code -> findNavController().navigate(NavGraphLaunchDirections.actionToFragmentDefendDialog())
                    }
                }
            }
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            binding.itemIcon.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Empty -> {
                    findNavController().navigate(NavGraphLaunchDirections.actionToFragmentSignIn())
                }
                is ViewState.Data -> {
                    val signIn = it.data
                    Toast.makeText(cxt, signIn.msg, Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        delay(500)
                        when (signIn.signCode) {
                            SignCode.SIGN_IN_SUC.code -> {
                                startActivity(Intent(act, Launch2Activity::class.java))
                                act.finish()
                            }
                            SignCode.SIGN_IN_FAIL.code -> {
                                findNavController().navigate(NavGraphLaunchDirections.actionToFragmentSignIn())
                            }
                        }
                    }
                }
            }
        }

        setFragmentResultListener(UpdateDialogFragment.REQUEST_KEY_UPDATE) { _, bundle ->
            bundle.getBoolean(UpdateDialogFragment.KEY_UPDATE).takeIf { it }?.let {
                checkGoogleServiceAndSignIn()
            }
        }

        publicVM.fetchAppConfig()
    }

    private fun checkGoogleServiceAndSignIn() {
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
            .addOnSuccessListener {
                FirebaseMsg.getInstance(complete = {
                    viewModel.checkSign()
                })
            }.addOnFailureListener {
                act.alertGoogleService()
            }
    }
}