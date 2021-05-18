package tw.north27.coachingapp.ui2.fragment.global

import android.os.Bundle
import android.view.View
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.yujie.basemodule.BaseFragment
import com.yujie.utilmodule.ext.showGoogleServiceAlert
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentLaunchBinding
import tw.north27.coachingapp.util2.bindImgBlurRes
import tw.north27.coachingapp.viewModel.LaunchViewModel


class LaunchFragment : BaseFragment<FragmentLaunchBinding>(R.layout.fragment_launch) {

    override val viewBindingFactory: (View) -> FragmentLaunchBinding
        get() = FragmentLaunchBinding::bind

    private val viewModel by sharedViewModel<LaunchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackground.bindImgBlurRes(R.drawable.ic_launch_background)
        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
            .addOnSuccessListener {
                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    it.result?.takeIf { it.isNotEmpty() }?.let { fcmToken ->
                        Timber.d("fcmToken = $fcmToken")
                        viewModel.getAppConfig(fcmToken)
                        
                    }
                }
            }.addOnFailureListener {
                act.showGoogleServiceAlert()
            }

//        viewModel.appConfigState.observe(viewLifecycleOwner) {
//            when (it) {
//                is ViewState.Initial -> {
//                    binding.ivIcon.startAlphaBreatheAnim()
//                }
//                is ViewState.Load -> {
//                }
//                is ViewState.Empty -> {
//                }
//                is ViewState.Data -> {
//                    val appConfig = it.data
//                    when (appConfig.appState) {
//                        AppState.MAINTAIN -> {
////                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
//                        }
//                        AppState.RUN -> {
//                            appConfig.updateInfo?.let {
//                                act.updateApp(it.url, UpdateHttpManager(cxt, it)) {
//                                    topPic = R.mipmap.ic_version_pic
//                                    setUpdateDialogFragmentListener { viewModel.checkSignIn() }
//                                }.check {
//                                    noNewApp { viewModel.checkSignIn() }
//                                }
//                            }
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