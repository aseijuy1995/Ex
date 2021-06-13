package tw.north27.coachingapp.ui//package tw.north27.coachingapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import com.yujie.utilmodule.base.BaseFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.ext.hideKeyBoard
import com.yujie.utilmodule.ext.observe
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.model.SignInState
import tw.north27.coachingapp.ui2.Launch2Activity
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    override val viewBindingFactory: (View) -> FragmentSignInBinding
        get() = FragmentSignInBinding::bind

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.touches { it.action == MotionEvent.ACTION_UP }.observe(viewLifecycleOwner) {
            act.hideKeyBoard()
        }

        binding.btnSignIn.clicksObserve(owner = viewLifecycleOwner) {
            val account = binding.etAccount.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signIn(account, password)
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            binding.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            when (it) {
                is ViewState.Load -> {
                    LoadingDialogFragment.show(parentFragmentManager)
                }
                is ViewState.Empty -> {
                    LoadingDialogFragment.dismiss()
                    Snackbar.make(binding.root, it.str!!, Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    LoadingDialogFragment.dismiss()
                    val signIn = it.data
                    when (signIn.signInState) {
                        SignInState.SIGN_IN -> {
                            lifecycleScope.launch {
                                Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
                                delay(200)
                                startActivity(Intent(act, Launch2Activity::class.java))
                                act.finish()
                            }
                        }
                        SignInState.SIGN_OUT -> {
                            Toast.makeText(cxt, signIn.signOutInfo?.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is ViewState.Error, is ViewState.Network -> {
                    LoadingDialogFragment.dismiss()
                }
            }
        }
    }

}