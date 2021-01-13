package edu.yujie.socketex.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.socketex.App.Companion.REQUEST_CODE_ALBUM
import edu.yujie.socketex.App.Companion.REQUEST_CODE_AUDIO
import edu.yujie.socketex.App.Companion.REQUEST_CODE_CAPTURE
import edu.yujie.socketex.R
import edu.yujie.socketex.base.BaseDialogFragment
import edu.yujie.socketex.bean.FeaturesBtn
import edu.yujie.socketex.databinding.FragmentChatRoomAddDialogBinding
import edu.yujie.socketex.vm.ChatRoomViewModel
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomAddDialogFragment : BaseDialogFragment<FragmentChatRoomAddDialogBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_room_add_dialog

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private lateinit var rxPermission: RxPermissions

    private val chatRoomAddList = listOf<FeaturesBtn>(
        FeaturesBtn(R.string.camera, R.drawable.ic_baseline_photo_camera_24_blue),
        FeaturesBtn(R.string.album, R.drawable.ic_baseline_photo_24_blue),
        FeaturesBtn(R.string.audio, R.drawable.ic_baseline_mic_24_blue)
    )

    private var fileName: String? = null
    private lateinit var captureUri: Uri

    override fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            featuresBtn = this@ChatRoomAddDialogFragment.chatRoomAddList
        }
        rxPermission = RxPermissions(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewCamera.viewItem
            .clicks()
            .compose(
                rxPermission.ensure(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ).subscribe {
                if (it) {
                    fileName = "Image_${System.nanoTime()}.jpg"
                    viewModel.startCapture(fileName!!) {
                        startActivityForResult(it, REQUEST_CODE_CAPTURE)
                    }
                } else {
                    Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)

        binding.viewAlbum.viewItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribe {
            if (it) {
                viewModel.startAlbum {
                    startActivityForResult(Intent.createChooser(it, "Select Picture"), REQUEST_CODE_ALBUM)
                }
            } else {
                Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
            }
        }.addTo(compositeDisposable)

        binding.viewAudio.viewItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribe {
            if (it) {
                viewModel.openMic()
            } else {
                Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        }.addTo(compositeDisposable)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.resultCapture()
                }
                findNavController().navigateUp()
            }
            REQUEST_CODE_ALBUM -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.resultAlbum(data)
                }
                findNavController().navigateUp()
            }
            REQUEST_CODE_AUDIO -> {
                if (resultCode == Activity.RESULT_OK) {

                }

            }
        }
    }

}