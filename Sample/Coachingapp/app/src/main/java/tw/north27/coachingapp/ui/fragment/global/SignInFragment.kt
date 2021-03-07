package tw.north27.coachingapp.ui.fragment.global

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.SignInInfo
import tw.north27.coachingapp.util.FirebaseManager
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding<FragmentSignInBinding>(FragmentSignInBinding::bind)

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.clicks().subscribeWithRxLife {
            showLoadingDialog()
            val account = binding.etAccount.text.toString()
            val password = binding.etPassword.text.toString()
            FirebaseManager.get().tokenRelay.subscribeWithRxLife {

            }
            viewModel.checkSignIn(account, password)
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

        viewModel.signIn.observe(viewLifecycleOwner, ::onSignInObs)
    }

    private fun onToastObs(pair: Pair<SignInViewModel.ToastType, String>) {
        when (pair.first) {
            SignInViewModel.ToastType.SIGN_IN -> {
                dismissLoadingDialog()
                Snackbar.make(binding.root, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onSignInObs(info: SignInInfo) {
        dismissLoadingDialog()
        //進入通道
        findNavController().navigate(NavGraphDirections.actionToFragmentHome())
    }


}