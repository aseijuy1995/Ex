package tw.north27.coachingapp.ui

import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStartBinding


class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override val viewBindingFactory: (View) -> FragmentStartBinding
        get() = FragmentStartBinding::bind

//    private val viewModel by sharedViewModel<StartViewModel>()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
//            .addOnSuccessListener {
//                FirebaseMsg.getInstance(complete = { viewModel.getAppConfig() })
//            }.addOnFailureListener {
//                act.alertGoogleService()
//            }
//
//        viewModel.appConfigState.observe(viewLifecycleOwner) {
//            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
//            when (it) {
//                is ViewState.Data -> {
//                    val appConfig = it.data
//                    when (appConfig.appState) {
//                        AppState.MAINTAIN -> {
//                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
//                        }
//                        AppState.RUN -> {
//                            val updateInfo = appConfig.updateInfo!!
//                            act.updateApp(updateInfo.versionName).builder {
//                                versionNameMode = UpdateApp.VersionNameMode.DEFAULT
//                            }.execute(
//                                newVersion = { _, _ -> findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog()) },
//                                noNewVersion = { viewModel.checkSignIn() })
//                        }
//                    }
//                }
//                //FIXME　整合處理各頁面錯誤
//                is ViewState.Error, is ViewState.Network -> {
////                    viewModel.getAppConfig()
//                }
//            }
//        }
//
////        setFragmentResultListener(UpdateDialogFragment.REQUEST_KEY_UPDATE_CLOSE) { _, bundle ->
////            bundle.getBoolean(UpdateDialogFragment.KEY_UPDATE_CLOSE).takeIf { it }?.let { viewModel.checkSignIn() }
////        }
//
//        viewModel.signInState.observe(viewLifecycleOwner) {
//            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
//            when (it) {
//                is ViewState.Empty -> {
//                    findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
//                }
//                is ViewState.Data -> {
//                    val signIn = it.data
//                    when (signIn.signInState) {
//                        SignInState.SIGN_IN -> {
//                            lifecycleScope.launch {
//                                Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
//                                delay(500)
//                                startActivity(Intent(act, Launch2Activity::class.java))
//                                act.finish()
//                            }
//                        }
//                        SignInState.SIGN_OUT -> {
//                            lifecycleScope.launch {
//                                Toast.makeText(cxt, signIn.signOutInfo?.msg, Toast.LENGTH_SHORT).show()
//                                delay(500)
//                                findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
//                            }
//                        }
//                    }
//                }
//                is ViewState.Error, is ViewState.Network -> {
//
//                }
//            }
//        }
//    }

}