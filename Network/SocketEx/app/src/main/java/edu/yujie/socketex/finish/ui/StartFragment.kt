package edu.yujie.socketex.finish.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragStartBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingDialogFragment
import edu.yujie.socketex.finish.inter.SignInState
import edu.yujie.socketex.finish.vm.StartViewModel
import edu.yujie.socketex.util.UpdateAppHttpUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : BaseViewBindingDialogFragment<FragStartBinding>(FragStartBinding::inflate) {

    private val viewModel by viewModel<StartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewModel.getInitData().observe(viewLifecycleOwner) {
            requireActivity().updateApp(it.apkFileUrl, UpdateAppHttpUtil(requireContext(), it)) {
                topPic = R.mipmap.init_pic
                setUpdateDialogFragmentListener { viewModel.checkSignIn() }
            }.check {
                onAfter { findNavController().navigateUp() }
                noNewApp { viewModel.checkSignIn() }
            }
        }

        viewModel.signInState.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                delay(1500)
                when (it) {
                    SignInState.NOT_SIGN_IN -> findNavController().navigate(StartFragmentDirections.actionFragStartToFragSignIn())

                    SignInState.ERROR_ACT_PWD -> {
                        Toast.makeText(requireContext(), "帳號 & 密碼已被修改，請重新登入", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(StartFragmentDirections.actionFragStartToFragSignIn())
                    }
                    SignInState.SIGN_IN -> findNavController().navigate(StartFragmentDirections.actionFragStartToFragHome())
                }
            }
        }

    }

    private fun initView() {
        findNavController().navigate(R.id.frag_loading_dialog)
    }

    private fun navToNext() {
        lifecycleScope.launch {
            delay(1500)
            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentChatRoom())
        }
    }

}