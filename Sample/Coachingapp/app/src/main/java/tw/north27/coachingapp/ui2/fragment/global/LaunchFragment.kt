package tw.north27.coachingapp.ui2.fragment.global

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import com.yujie.basemodule.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
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
        initView()

//        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
//            .addOnSuccessListener {
//                FirebaseMessaging.getInstance().token.addOnCompleteListener {
//                    if (!it.isSuccessful) return@addOnCompleteListener
//                    it.result?.takeIf { it.isNotEmpty() }?.let { fcmToken ->
//                        Timber.d("fcmToken = $fcmToken")
//                        viewModel.getAppConfig(fcmToken)
//                    }
//                }
//            }.addOnFailureListener {
//        Alerter.create(act)
//            .setIcon(R.drawable.ic_baseline_error_24_white)
//            .setTitle(getString(R.string.google_play_service_title))
//            .setText(R.string.google_play_service_text)
//            .setBackgroundColor(R.color.yellow_f7cd3b)
//            .show()
//            }

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

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            act.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            act.window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                statusBarColor = resources.getColor(android.R.color.transparent)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            act.window.decorView.windowInsetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            act.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        binding.ivBackground.bindImgBlurRes(R.drawable.ic_launch_background)
    }

}