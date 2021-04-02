package tw.north27.coachingapp.ui.fragment.global

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.util.ViewState
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>(FragmentSignInBinding::bind)

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.touches { it.action == MotionEvent.ACTION_UP }.subscribeWithRxLife {
            act.hideKeyBoard()
        }

        binding.btnSignIn.clickThrottleFirst().subscribeWithRxLife {
            showLoadingDialog()
            val account = binding.etAccount.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signIn(account, password)
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Load -> {
                }
                is ViewState.Empty -> {
                    dismissLoadingDialog()
                    Snackbar.make(binding.root, it.str!!, Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    dismissLoadingDialog()
                    findNavController().navigate(NavGraphDirections.actionToFragmentHome())
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