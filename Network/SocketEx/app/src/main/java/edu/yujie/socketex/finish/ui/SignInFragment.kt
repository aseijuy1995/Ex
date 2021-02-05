package edu.yujie.socketex.finish.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragSignInBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingDialogFragment
import edu.yujie.socketex.finish.inter.SignInStatus
import edu.yujie.socketex.finish.vm.StartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : BaseViewBindingDialogFragment<FragSignInBinding>(FragSignInBinding::inflate) {

    private val viewModel by viewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.clicks().subscribeWithLife {
            findNavController().navigate(R.id.frag_loading_dialog)
            val account = binding.etAccount.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.checkSignIn(account, password)
        }

        viewModel.signInStatus.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.frag_loading_dialog)
            when (it) {
                SignInStatus.ERROR_ACT_PWD -> Snackbar.make(binding.root, "帳號密碼錯誤", Snackbar.LENGTH_SHORT).show()
                SignInStatus.SIGN_IN -> {
                    Snackbar.make(binding.root, "登入成功,準備跳轉頁面...", Snackbar.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        delay(1500L)
                        findNavController().navigate(StartFragmentDirections.actionFragStartToFragHome())
                    }

                }
            }
        }
    }


}