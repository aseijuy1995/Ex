package tw.north27.coachingapp.ui

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
import tw.north27.coachingapp.model.SignInCode
import tw.north27.coachingapp.ui.launch2.Launch2Activity
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    override val viewBind: (View) -> FragmentSignInBinding
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
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Empty -> {
                    Snackbar.make(binding.root, it.str!!, Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    val signIn = it.data
                    Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
                    when (signIn.signInCode) {
                        SignInCode.SIGN_IN_SUC.code -> {
                            lifecycleScope.launch {
                                delay(200)
                                startActivity(Intent(act, Launch2Activity::class.java))
                                act.finish()
                            }
                        }
                        SignInCode.SIGN_IN_FAIL.code -> {
                        }
                    }
                }
            }
        }
    }

}