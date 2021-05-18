package tw.north27.coachingapp.ui2.fragment.global

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import com.yujie.basemodule.viewBinding
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.showErrorAlert
import com.yujie.utilmodule.ext.showNetworkAlert
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.ext2.clicksObserve
import tw.north27.coachingapp.ext2.hideKeyBoard
import tw.north27.coachingapp.ui.Launch2Activity
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.touches { it.action == MotionEvent.ACTION_UP }.subscribeWithRxLife {
            act.hideKeyBoard()
        }

        binding.btnSignIn.clicksObserve(owner = viewLifecycleOwner) {
            val account = binding.etAccount.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signIn(account, password)
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                    showLoadingDialog()
                }
                is ViewState.Empty -> {
                    dismissLoadingDialog()
                    Snackbar.make(binding.root, it.str!!, Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    dismissLoadingDialog()
//                    findNavController().navigate(NavGraphDirections.actionToFragmentHome())
                    startActivity(Intent(act, Launch2Activity::class.java))
                    act.finish()
                }
                is ViewState.Error -> {
                    act.showErrorAlert()
                }
                is ViewState.Network -> {
                    act.showNetworkAlert()
                }
            }
        }
    }

}