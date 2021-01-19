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
import edu.yujie.socketex.databinding.FragmentChatRoomAddBottomSheetDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomAddBottomSheetDialogFragment : BaseBottomSheetDialogFragment<FragmentChatRoomAddBottomSheetDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_room_add_bottom_sheet_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private lateinit var rxPermission: RxPermissions

    override fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomAddBottomSheetDialogFragment.viewModel
        }
        rxPermission = RxPermissions(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                println("$TAG onCameraResult2")
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
                println("$TAG onAlbumResult2")
                if (it) {
                    viewModel.createAlbumBuilder { startActivityForResult(it.intent, it.requestCode) }
                } else {
                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
                }
            }
        //recorder
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
                    viewModel.openMic()
                } else {
                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
                }
                findNavController().navigateUp()
            }
        //photography
        binding.viewPhotography.viewItem
            .clicks()
            .compose(
                rxPermission.ensure(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            .subscribeWithLife {
//                if(it){
//                    viewModel.openMic()
//                }else{
//                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
//                }
            }
        //video
        binding.viewVideo.viewItem
            .clicks()
            .compose(rxPermission.ensure(Manifest.permission.READ_EXTERNAL_STORAGE))
            .subscribeWithLife {
                if (it) {
                    viewModel.switchToVideo(true)
                } else {
                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
                }
                findNavController().navigateUp()
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
        viewModel.onAlbumResult(requestCode, resultCode, data).subscribe {
            when (it) {
                is IntentResult.IntentResultSuccess -> {
                    println("$TAG onAlbumResult")
                    viewModel.albumResultEvent(it)
                    findNavController().navigateUp()
                }
                is IntentResult.IntentResultFailed -> {
                    Snackbar.make(binding.viewCamera.viewItem, "Please select Photo!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
                    findNavController().navigateUp()
                }
            }
        }
    }
}
