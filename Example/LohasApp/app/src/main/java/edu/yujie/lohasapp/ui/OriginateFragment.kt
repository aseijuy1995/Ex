package edu.yujie.lohasapp.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.vector.update_app_kotlin.check
import com.vector.update_app_kotlin.updateApp
import edu.yujie.lohasapp.IApiService
import edu.yujie.lohasapp.R
import edu.yujie.lohasapp.databinding.FragOriginateBinding
import edu.yujie.lohasapp.util.UpdateAppHttpUtil
import edu.yujie.lohasapp.viewModel.OriginateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OriginateFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private val service by inject<IApiService>()
    private val viewModel by viewModel<OriginateViewModel>()
    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragOriginateBinding.inflate(inflater, container, false)

        viewModel.postVersion().observe(viewLifecycleOwner) { appResult ->
            requireActivity().updateApp("Update_URL", UpdateAppHttpUtil(requireContext(), lifecycleScope, service)) {
                isPost = true
                hideDialogOnDownloading()
                topPic = R.mipmap.pic
                targetPath = appResult.applestoreurl
                setUpdateDialogFragmentListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(1500)
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(R.id.action_to_frag_home)
                        }
                    }
                }
            }.check {
                onBefore {
                    dialog = ProgressDialog.show(requireContext(), "Alert", "Loading...")
                    dialog?.show()
                }
                noNewApp {
                    Snackbar.make(binding.root, "Not need to Update", Snackbar.LENGTH_SHORT).show()
                    lifecycleScope.launch(Dispatchers.IO) {
                        delay(1500)
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(R.id.action_to_frag_home)
                        }
                    }
                }
                onAfter {
                    dialog?.dismiss()
                }
            }
        }

        return binding.root
    }
}