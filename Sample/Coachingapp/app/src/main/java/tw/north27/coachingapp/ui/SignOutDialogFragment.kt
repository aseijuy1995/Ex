package tw.north27.coachingapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentSignOutDialogBinding
import tw.north27.coachingapp.model.SignInState
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.SignOutViewModel

class SignOutDialogFragment : BaseDialogFragment<FragmentSignOutDialogBinding>(R.layout.fragment_sign_out_dialog) {

    override val viewBind: (View) -> FragmentSignOutDialogBinding
        get() = FragmentSignOutDialogBinding::bind

    private val viewModel by viewModel<SignOutViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signOutState.observe(viewLifecycleOwner) {
            if (it is ViewState.Load) LoadingDialogFragment.show(parentFragmentManager)
            if (it !is ViewState.Initial && it !is ViewState.Load) LoadingDialogFragment.dismiss()
            when (it) {
                is ViewState.Data -> {
                    val signIn = it.data
                    when (signIn.signInState) {
                        SignInState.SIGN_OUT -> {
                            lifecycleScope.launch {
                                Toast.makeText(cxt, signIn.signOutInfo?.msg, Toast.LENGTH_SHORT).show()
                                delay(200)
                                act.finishAffinity()
                            }
                        }
                        SignInState.SIGN_IN -> {
                            Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                //FIXME　整合處理各頁面錯誤
                is ViewState.Error, is ViewState.Network -> {
                }
            }
        }

        binding.btnSignOut.clicksObserve(owner = viewLifecycleOwner) {
            viewModel.signOut()
        }

        binding.ivClose.clicksObserve(owner = viewLifecycleOwner) {
            findNavController().navigateUp()
        }
    }
}