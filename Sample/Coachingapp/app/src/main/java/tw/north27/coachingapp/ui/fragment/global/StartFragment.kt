package tw.north27.coachingapp.ui.fragment.global

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.messaging.FirebaseMessaging
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentStartBinding
import tw.north27.coachingapp.ext.errorAlert
import tw.north27.coachingapp.ext.networkAlert
import tw.north27.coachingapp.ext.startAlphaBreatheAnim
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.http.UpdateHttpManager
import tw.north27.coachingapp.model.result.AppState
import tw.north27.coachingapp.model.result.SignState
import tw.north27.coachingapp.ui.CoachingActivity
import tw.north27.coachingapp.util.ViewState
import tw.north27.coachingapp.viewModel.StartViewModel

class StartFragment : BaseFragment(R.layout.fragment_start) {

    private val binding by viewBinding(FragmentStartBinding::bind)

    private val viewModel by sharedViewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (!it.isSuccessful) return@addOnCompleteListener
            val fcmToken = it.result ?: ""
            Timber.d("fcmToken = $fcmToken")
            viewModel.getAppConfig(fcmToken)
        }

        viewModel.appConfigState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                    binding.ivIcon.startAlphaBreatheAnim()
                }
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
                }
                is ViewState.Data -> {
                    val appConfig = it.data
                    when (appConfig.appState) {
                        AppState.MAINTAIN -> {
//                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentMaintainDialog())
                        }
                        AppState.RUN -> {
                            appConfig.updateInfo?.let {
                                act.updateApp(it.url, UpdateHttpManager(cxt, it)) {
                                    topPic = R.mipmap.ic_version_pic
                                    setUpdateDialogFragmentListener { viewModel.checkSignIn() }
                                }.check {
                                    noNewApp { viewModel.checkSignIn() }
                                }
                            }
                        }
                    }
                }
                is ViewState.Error -> {
                    act.errorAlert()
                }
                is ViewState.Network -> {
                    act.networkAlert()
                }
            }
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
//                    findNavController().navigate(NavDirections.actionToFragmentSignIn())
                }
                is ViewState.Data -> {
                    val signIn = it.data
                    when (signIn.signState) {
                        SignState.SIGN_IN_SUCCESS -> {
//                            findNavController().navigate(NavGraphDirections.actionToFragmentHome())
                            startActivity(Intent(act, CoachingActivity::class.java))
                            act.finish()
                        }
                        SignState.SIGN_IN_FAILURE -> {
//                            findNavController().navigate(NavGraphDirections.actionToFragmentSignIn())
                        }
                    }
                }
                is ViewState.Error -> {
                    act.errorAlert()
                }
                is ViewState.Network -> {
                    act.networkAlert()
                }
            }
        }
    }

}