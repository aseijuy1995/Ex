package tw.north27.coachingapp.ui2.fragment.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.tbruyelle.rxpermissions3.RxPermissions
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.chat.ChatRoomAddViewModel
import tw.north27.coachingapp.databinding.FragmentChatRoomAddDialogBinding

class ChatRoomAddDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomAddDialogBinding>(FragmentChatRoomAddDialogBinding::inflate) {

    private val viewModel by viewModel<ChatRoomAddViewModel>()

    private lateinit var rxPermission: RxPermissions

    companion object {
        val REQUEST_KEY_CHAT_ROOM_ADD = "REQUEST_KEY_CHAT_ROOM_ADD"

        val KEY_MEDIA_PERMISSION = "KEY_MEDIA_PERMISSION"

        val KEY_MEDIA_FEATURE = "KEY_MEDIA_FEATURE"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomAddDialogFragment.viewModel
        }
        rxPermission = RxPermissions(this)

        //camera
        binding.itemCamera.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.CAMERA
                )
            )
            findNavController().navigateUp()
        }

        //photo
        binding.itemPhoto.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.PHOTO
                )
            )
            findNavController().navigateUp()
        }

        //mic
        binding.itemMic.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.MIC
                )
            )
            findNavController().navigateUp()
        }

        //audio
        binding.itemAudio.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.AUDIO
                )
            )
            findNavController().navigateUp()
        }

        //video
        binding.itemVideo.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.VIDEO
                )
            )
            findNavController().navigateUp()
        }

        //movie
        binding.itemMovie.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            setFragmentResult(
                REQUEST_KEY_CHAT_ROOM_ADD, bundleOf(
                    KEY_MEDIA_PERMISSION to it,
                    KEY_MEDIA_FEATURE to ChatRoomAddViewModel.MediaFeature.MOVIE
                )
            )
            findNavController().navigateUp()
        }

    }

}
