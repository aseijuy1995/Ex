package edu.yujie.socketex.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.databinding.FragmentAddDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddDialogFragment : BaseBottomSheetDialogFragment<FragmentAddDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_add_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private lateinit var rxPermission: RxPermissions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //camera
        binding.viewCamera.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToCamera()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewAudio.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
        //album
        binding.viewAlbum.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToAlbum()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewAlbum.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
        //audio
        binding.viewAudio.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToAudio()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewAudio.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
        //recording
        binding.viewRecording.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToRecording()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewAudio.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
        //photography
        binding.viewPhotography.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToPhotography()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewVideo.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
        //video
        binding.viewVideo.viewItem.clicks().compose(rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE)).subscribeWithLife {
            if (it) {
                viewModel.switchToVideo()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.viewVideo.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
            }
        }
    }

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddDialogFragment.viewModel
        }
        rxPermission = RxPermissions(this)
    }

}
