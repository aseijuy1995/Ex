package edu.yujie.socketex.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragmentStartBinding
import edu.yujie.socketex.finish.base.fragment.BaseViewBindingDialogFragment
import edu.yujie.socketex.util.UpdateAppHttpUtil
import edu.yujie.socketex.vm.StartVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : BaseViewBindingDialogFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {

    private val vm by viewModel<StartVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        vm.getInitData().observe(viewLifecycleOwner) {
            println("it = $it")
            requireActivity().updateApp("Update_URL", UpdateAppHttpUtil(requireContext(), it)) {
//                hideDialogOnDownloading()
                topPic = R.mipmap.init_pic
                setUpdateDialogFragmentListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(1500)
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(StartFragmentDirections.actionFragmentStartToFragmentChatRoom())
                        }
                    }
                }
            }.check {
                onAfter { navController.popBackStack() }
            }
        }
    }

    private fun initView() {
        findNavController().navigate(R.id.frag_loading_dialog)
    }
}