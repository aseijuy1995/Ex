package tw.north27.coachingapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.NavGraphDirections
import tw.north27.coachingapp.base.view.BaseViewBindingFragment
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseViewBindingFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.clicks().subscribeWithLife {
            showLoadingDialog()
            val account = binding.etAccount.text.toString()
            val password = binding.etPassword.text.toString()
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

    private fun onSignInObs(result: SignInResult) {
        dismissLoadingDialog()
//        findNavController().navigate(NavGraphDirections)
    }


}