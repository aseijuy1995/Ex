package tw.north27.coachingapp.ui.launch

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.touches
import com.yujie.core_lib.base.BaseFragment
import com.yujie.core_lib.ext.clicksObserve
import com.yujie.core_lib.ext.hideKeyBoard
import com.yujie.core_lib.ext.observe
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentSignInBinding
import tw.north27.coachingapp.model.SignCode
import tw.north27.coachingapp.ui.launch2.basic.Launch2Activity
import tw.north27.coachingapp.ui.launch2.share.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.SignInViewModel

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    override val viewBind: (View) -> FragmentSignInBinding
        get() = FragmentSignInBinding::bind

    private val viewModel by viewModel<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signInState.observe(viewLifecycleOwner) {
            binding.itemIcon.svkView.isVisible = (it !is ViewState.Initial) and (it is ViewState.Load)
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Empty -> {
                    Snackbar.make(binding.root, it.str!!, Snackbar.LENGTH_SHORT).show()
                }
                is ViewState.Data -> {
                    val signIn = it.data
                    Toast.makeText(cxt, signIn.msg, Toast.LENGTH_SHORT).show()
                    when (signIn.signCode) {
                        SignCode.SIGN_IN_SUC.code -> {
                            lifecycleScope.launch {
                                delay(200)
                                startActivity(Intent(act, Launch2Activity::class.java))
                                act.finish()
                            }
                        }
                        SignCode.SIGN_IN_FAIL.code -> {
                        }
                    }
                }
            }
        }

        binding.root.touches { it.action == MotionEvent.ACTION_UP }.observe(viewLifecycleOwner) {
            act.hideKeyBoard()
        }

        binding.btnSignIn.clicksObserve(owner = viewLifecycleOwner) {
            val account = binding.etAccount.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signIn(account, password)
        }

    }
}