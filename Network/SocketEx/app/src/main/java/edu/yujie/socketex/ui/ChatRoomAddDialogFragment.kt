package edu.yujie.socketex.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxrelay3.BehaviorRelay
import com.tbruyelle.rxpermissions3.RxPermissions
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseDialogFragment
import edu.yujie.socketex.bean.FeaturesBtn
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.databinding.FragmentChatRoomAddDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomAddDialogFragment : BaseDialogFragment<FragmentChatRoomAddDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_room_add_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private lateinit var rxPermission: RxPermissions

    private var albumBehaviorRelay: BehaviorRelay<IntentResult>? = null

    private val chatRoomAddList = listOf<FeaturesBtn>(
        FeaturesBtn(R.string.camera, R.drawable.ic_baseline_photo_camera_24_gray),
        FeaturesBtn(R.string.album, R.drawable.ic_baseline_photo_24_gray),
        FeaturesBtn(R.string.audio, R.drawable.ic_baseline_mic_none_24_gray)
    )

    override fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            featuresBtn = this@ChatRoomAddDialogFragment.chatRoomAddList
        }
        rxPermission = RxPermissions(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //camera
        binding.viewCamera.viewItem
            .clicks()
            .bindToLifecycle(this)
            .compose(
                rxPermission.ensure(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribe { cameraEvent(it) }
        //album
        binding.viewAlbum.viewItem
            .clicks()
            .bindToLifecycle(this)
            .compose(
                rxPermission.ensure(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribe { albumEvent(it) }
        //recorder
        binding.viewAudio.viewItem
            .clicks()
            .bindToLifecycle(this)
            .compose(
                rxPermission.ensure(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribe { micEvent(it) }


    }

    private fun cameraEvent(it: Boolean) {
        if (it) {
            viewModel.createCameraBuilder().bindToLifecycle(this).subscribe {
                println("createCameraBuilder")
                startActivityForResult(it.intent, it.requestCode)
            }
        } else {
            Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun albumEvent(it: Boolean) {
        if (it) {
            viewModel.buildAlbum {
                startActivityForResult(it.intent, it.requestCode)
            }
        } else {
            Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun micEvent(it: Boolean) {
        if (it) {
            viewModel.openMic()
        } else {
            Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
        }
        findNavController().navigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentResult(requestCode, resultCode, data)
        cameraResult(result)
        albumResult(result)
    }

    private fun cameraResult(result: IntentResult) {
        viewModel.onCameraResult(result)
            .bindToLifecycle(this)
            .subscribe {
                println("createCameraBuilder2")
                when (it) {
                    is IntentResult.IntentResultSuccess -> {
                        viewModel.cameraResultEvent(it)
                        findNavController().navigateUp()
                    }
                    is IntentResult.IntentResultFailed -> {
                        Snackbar.make(binding.viewCamera.viewItem, "Please take pictures!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun albumResult(result: IntentResult) {
        if (albumBehaviorRelay == null) {
            albumBehaviorRelay = viewModel.onAlbumResult(result)
            albumBehaviorRelay
                ?.bindToLifecycle(this)
                ?.subscribe {
                    when (it) {
                        is IntentResult.IntentResultSuccess -> {
                            viewModel.albumResultEvent(it)
                            findNavController().navigateUp()
                        }
                        is IntentResult.IntentResultFailed -> {
                            Snackbar.make(binding.viewCamera.viewItem, "Please select Photo!", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
        } else {
            albumBehaviorRelay = viewModel.onAlbumResult(result)
        }
    }

}