package edu.yujie.socketex.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseBottomSheetDialogFragment
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.databinding.FragmentAddBottomSheetDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentAddBottomSheetDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_add_bottom_sheet_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private lateinit var rxPermission: RxPermissions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddBottomSheetDialogFragment.viewModel
        }
        rxPermission = RxPermissions(this)
        //
        //camera
        binding.viewCamera.viewItem
            .clicks()
            .compose(
                rxPermission.ensure(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribeWithLife {
                if (it) {
                    viewModel.createCameraBuilder { startActivityForResult(it.intent, it.requestCode) }
                } else {
                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
                }
            }
        //album
        binding.viewAlbum.viewItem
            .clicks()
            .compose(
                rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE)
            ).subscribeWithLife {
                if (it) {
                    viewModel.switchToAlbum()
                    findNavController().navigateUp()
                } else {
                    Snackbar.make(binding.viewAlbum.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                }
//                if (it) {
//                    viewModel.createAlbumBuilder { startActivityForResult(it.intent, it.requestCode) }
//                } else {
//                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
//                }
            }
        //audio
        binding.viewAudio.viewItem
            .clicks()
            .compose(
                rxPermission.ensure(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribeWithLife {
                if (it) {
                    viewModel.switchToAudio()
                    findNavController().navigateUp()
                } else {
                    Snackbar.make(binding.viewAudio.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                }
            }
        //photography
        binding.viewPhotography.viewItem
            .clicks()
            .compose(rxPermission.ensure(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .subscribeWithLife {
            }
        //video
        binding.viewVideo.viewItem
            .clicks()
            .compose(rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
            .subscribeWithLife {
                if (it) {
                    viewModel.switchToVideo()
                    findNavController().navigateUp()
                } else {
                    Snackbar.make(binding.viewVideo.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                }
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onCameraResult(requestCode, resultCode, data).subscribe {
            when (it) {
                is IntentResult.IntentResultSuccess -> {
                    viewModel.createCropBuilder(it.uris!!.first()) { startActivityForResult(it.intent, it.requestCode) }
                }
                is IntentResult.IntentResultFailed -> {
                    Snackbar.make(binding.viewCamera.viewItem, "Please take pictures!", Snackbar.LENGTH_SHORT).setAnchorView(requireActivity().window.decorView).show()
                    findNavController().navigateUp()
                }
            }
        }
        viewModel.onCropResult(requestCode, resultCode, data).subscribe {
            when (it) {
                is IntentResult.IntentResultSuccess -> {
                    viewModel.cameraCropResultEvent(it)
                    findNavController().navigateUp()
                }
                is IntentResult.IntentResultFailed -> {
                    Snackbar.make(binding.viewCamera.viewItem, "Please Crop photo!", Snackbar.LENGTH_SHORT).setAnchorView(requireActivity().window.decorView).show()
                    findNavController().navigateUp()
                }
            }
        }
//        viewModel.onAlbumResult(requestCode, resultCode, data).subscribe {
//            when (it) {
//                is IntentResult.IntentResultSuccess -> {
//                    println("$TAG onAlbumResult")
//                    viewModel.albumResultEvent(it)
//                    findNavController().navigateUp()
//                }
//                is IntentResult.IntentResultFailed -> {
//                    Snackbar.make(binding.viewCamera.viewItem, "Please select Photo!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
//                    findNavController().navigateUp()
//                }
//            }
//        }
    }
}
