package tw.north27.coachingapp.ui.launch

import android.view.View
import com.yujie.utilmodule.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override val viewBind: (View) -> FragmentStartBinding
        get() = FragmentStartBinding::bind

    private val viewModel by sharedViewModel<StartViewModel>()

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.appConfigState.observe(viewLifecycleOwner) {
//            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
//            when (it) {
//                is ViewState.Data -> {
//                    val appConfig = it.data
//                    when (appConfig.appCode) {
//                        AppCode.DEFEND.code -> {
//                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
//                        }
//                        AppCode.MOTION.code -> {
//                            val updateInfo = appConfig.motionInfo!!
//                            act.updateApp(updateInfo.versionName).builder {
//                                versionNameMode = UpdateApp.VersionNameMode.DEFAULT
//                            }.execute(
//                                newVersion = { _, _ ->
//                                    findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentUpdateDialog())
//                                },
//                                noNewVersion = { checkGoogleServiceAndSignIn() }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//
//        viewModel.signInState.observe(viewLifecycleOwner) {
//            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
//            when (it) {
//                is ViewState.Empty -> {
//                    findNavController().navigate(NavGraphLaunchDirections.actionToFragmentSignIn())
//                }
//                is ViewState.Data -> {
//                    val signIn = it.data
//                    Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
//                    lifecycleScope.launch {
//                        delay(200)
//                        when (signIn.signInCode) {
//                            SignInCode.SIGN_IN_SUC.code -> {
//                                startActivity(Intent(act, Launch2Activity::class.java))
//                                act.finish()
//                            }
//                            SignInCode.SIGN_IN_FAIL.code -> {
//                                findNavController().navigate(NavGraphLaunchDirections.actionToFragmentSignIn())
//                            }
//                        }
//
//                    }
//                }
//            }
//        }
//
//        setFragmentResultListener(UpdateDialogFragment.REQUEST_KEY_UPDATE_CLOSE) { _, bundle ->
//            bundle.getBoolean(UpdateDialogFragment.KEY_UPDATE_CLOSE).takeIf { it }?.let {
//                checkGoogleServiceAndSignIn()
//            }
//        }
//
//        viewModel.fetchAppConfig()
//    }
//
//    private fun checkGoogleServiceAndSignIn() {
//        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(act)
//            .addOnSuccessListener {
//                FirebaseMsg.getInstance(complete = {
//                    viewModel.checkSignIn()
//                })
//            }.addOnFailureListener {
//                act.alertGoogleService()
//            }
//    }
}