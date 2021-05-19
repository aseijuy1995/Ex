package tw.north27.coachingapp.ui2.public

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.yujie.basemodule.BaseDialogFragment
import com.yujie.utilmodule.ViewState
import com.yujie.utilmodule.ext.showErrorAlert
import com.yujie.utilmodule.ext.showNetworkAlert
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentExitDialogBinding
import tw.north27.coachingapp.ext2.clicksObserve
import tw.north27.coachingapp.model.result.SignInState

class ExitDialogFragment : BaseDialogFragment<FragmentExitDialogBinding>(R.layout.fragment_exit_dialog) {

    override val viewBindingFactory: (View) -> FragmentExitDialogBinding
        get() = FragmentExitDialogBinding::bind

    private val viewModel by viewModel<ExitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MaintainDialogTheme)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.clicksObserve(owner = viewLifecycleOwner) {
            viewModel.signOut()
        }

        binding.ivClose.clicksObserve(owner = viewLifecycleOwner) {
            dismiss()
        }

        viewModel.signOutState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
//                    showLoadingDialog()
                }
                is ViewState.Empty -> {
//                    dismissLoadingDialog()
                }
                is ViewState.Data -> {
//                    dismissLoadingDialog()
                    val signState = it.data.signInState
                    if (signState == SignInState.SIGN_OUT) {
                        act.finishAffinity()
                        Toast.makeText(cxt, cxt.getString(R.string.sign_out_success), Toast.LENGTH_SHORT).show()
                    }
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