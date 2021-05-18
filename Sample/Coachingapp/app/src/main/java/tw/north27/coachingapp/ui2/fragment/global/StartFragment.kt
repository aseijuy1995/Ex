package tw.north27.coachingapp.ui2.fragment.global

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.GoogleApiAvailability
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import com.yujie.basemodule.BaseFragment
import com.yujie.pushmodule.fcm.FirebaseMsg
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.showGoogleServiceAlert
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.util.UpdateHttpManager
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.util2.bindImgBlurRes
import tw.north27.coachingapp.viewModel.LaunchViewModel


class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override val viewBindingFactory: (View) -> FragmentStartBinding
        get() = FragmentStartBinding::bind

    private val viewModel by sharedViewModel<LaunchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackground.bindImgBlurRes(R.drawable.ic_launch_background)
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
            .addOnSuccessListener {
                FirebaseMsg.getInstance(complete = { viewModel.getAppConfig() })
            }.addOnFailureListener {
                act.showGoogleServiceAlert()
            }

        viewModel.appConfigState.observe(viewLifecycleOwner) {
            binding.spinKitView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Data -> {
                    val appConfig = it.data
                    when (appConfig.appState) {
                        AppState.MAINTAIN -> {
                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
                        }
                        AppState.RUN -> {
                            val updateInfo = appConfig.updateInfo!!
                            act.updateApp(updateInfo.url, UpdateHttpManager(cxt, updateInfo)) {
                                topPic = R.drawable.ic_background_update
                                setUpdateDialogFragmentListener { viewModel.checkSignIn() }
                            }.check { noNewApp { viewModel.checkSignIn() } }

                        }
                    }
                }
                is ViewState.Error, is ViewState.Network -> {
                    viewModel.getAppConfig()
                }
            }
        }

//        viewModel.signInState.observe(viewLifecycleOwner) {
//            when (it) {
//                is ViewState.Initial -> {
//                }
//                is ViewState.Load -> {
//                }
//                is ViewState.Empty -> {
////                    findNavController().navigate(NavDirections.actionToFragmentSignIn())
//                }
//                is ViewState.Data -> {
//                    val signIn = it.data
//                    when (signIn.signState) {
//                        SignState.SIGN_IN_SUCCESS -> {
////                            findNavController().navigate(NavGraphDirections.actionToFragmentHome())
//                            startActivity(Intent(act, HomeActivity::class.java))
//                            act.finish()
//                        }
//                        SignState.SIGN_IN_FAILURE -> {
////                            findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
//                        }
//                    }
//                }
//                is ViewState.Error -> {
//                    act.errorAlert()
//                }
//                is ViewState.Network -> {
//                    act.networkAlert()
//                }
//            }
//        }
    }

}