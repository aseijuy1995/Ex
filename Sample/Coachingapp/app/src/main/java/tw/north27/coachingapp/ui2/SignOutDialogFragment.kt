package tw.north27.coachingapp.ui2//package tw.north27.coachingapp.ui
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.lifecycle.lifecycleScope
//import androidx.navigation.fragment.findNavController
//import com.yujie.basemodule.BaseDialogFragment
//import com.yujie.utilmodule.ViewState
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import tw.north27.coachingapp.R
//import tw.north27.coachingapp.databinding.FragmentSignOutDialogBinding
//import tw.north27.coachingapp.ext2.clicksObserve
//import tw.north27.coachingapp.model.SignInState
//import tw.north27.coachingapp.ui2.fragment.global.LoadingDialogFragment
//import tw.north27.coachingapp.viewModel.SignOutViewModel
//
//class SignOutDialogFragment : BaseDialogFragment<FragmentSignOutDialogBinding>(R.layout.fragment_sign_out_dialog) {
//
//    override val viewBindingFactory: (View) -> FragmentSignOutDialogBinding
//        get() = FragmentSignOutDialogBinding::bind
//
//    private val viewModel by viewModel<SignOutViewModel>()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnSignOut.clicksObserve(owner = viewLifecycleOwner) {
//            viewModel.signOut()
//        }
//
//        binding.ivClose.clicksObserve(owner = viewLifecycleOwner) {
//            findNavController().navigateUp()
//        }
//
//        viewModel.signOutState.observe(viewLifecycleOwner) {
//            when (it) {
//                is ViewState.Load -> {
//                    LoadingDialogFragment.show(parentFragmentManager)
//                }
//                is ViewState.Data -> {
//                    LoadingDialogFragment.dismiss()
//                    val signIn = it.data
//                    when (signIn.signInState) {
//                        SignInState.SIGN_OUT -> {
//                            lifecycleScope.launch {
//                                Toast.makeText(cxt, signIn.signOutInfo?.msg, Toast.LENGTH_SHORT).show()
//                                delay(1500)
//                                act.finishAffinity()
//                            }
//                        }
//                        SignInState.SIGN_IN -> {
//                            Toast.makeText(cxt, signIn.signInInfo?.msg, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                is ViewState.Error, is ViewState.Network -> {
//                    LoadingDialogFragment.dismiss()
//                }
//            }
//        }
//    }
//}