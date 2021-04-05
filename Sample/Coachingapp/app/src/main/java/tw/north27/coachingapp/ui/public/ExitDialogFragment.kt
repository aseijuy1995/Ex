package tw.north27.coachingapp.ui.public

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseDialogFragment
import tw.north27.coachingapp.databinding.FragmentExitDialogBinding
import tw.north27.coachingapp.ext.clickThrottleFirst
import tw.north27.coachingapp.ext.errorAlert
import tw.north27.coachingapp.ext.networkAlert
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.SignState
import tw.north27.coachingapp.util.ViewState

class ExitDialogFragment : BaseDialogFragment(R.layout.fragment_exit_dialog) {

    private val binding by viewBinding(FragmentExitDialogBinding::bind)

    private val viewModel by viewModel<ExitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MaintainDialogTheme)
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.clickThrottleFirst().subscribeWithRxLife {
            viewModel.signOut()
        }

        binding.ivClose.clickThrottleFirst().subscribeWithRxLife {
            dismiss()
        }

        viewModel.signOutState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Initial -> {
                }
                is ViewState.Load -> {
                    showLoadingDialog()
                }
                is ViewState.Empty -> {
                    dismissLoadingDialog()
                }
                is ViewState.Data -> {
                    dismissLoadingDialog()
                    val signState = it.data.signState
                    if (signState == SignState.SIGN_OUT_SUCCESS) {
                        act.finishAffinity()
                        Toast.makeText(cxt, cxt.getString(R.string.sign_out_success), Toast.LENGTH_SHORT).show()
                    }
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