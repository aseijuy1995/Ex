package tw.north27.coachingapp.ui.launch2

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yujie.utilmodule.base.BaseDialogFragment
import com.yujie.utilmodule.ext.clicksObserve
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.FragmentSignOutDialogBinding
import tw.north27.coachingapp.ui.LoadingDialogFragment
import tw.north27.coachingapp.viewModel.SignOutViewModel

class SignOutDialogFragment : BaseDialogFragment<FragmentSignOutDialogBinding>(R.layout.fragment_sign_out_dialog) {

    override val viewBind: (View) -> FragmentSignOutDialogBinding
        get() = FragmentSignOutDialogBinding::bind

    private val viewModel by viewModel<SignOutViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isSignOut.observe(viewLifecycleOwner) {
            if (it) {
                LoadingDialogFragment.dismiss()
                lifecycleScope.launch(Dispatchers.IO) {
                    delay(200)
                    act.finishAffinity()
                }
            } else {
                LoadingDialogFragment.show(parentFragmentManager)
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