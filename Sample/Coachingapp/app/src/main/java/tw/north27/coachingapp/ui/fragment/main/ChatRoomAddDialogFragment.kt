package tw.north27.coachingapp.ui.fragment.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.base.BaseViewBindingBottomSheetDialogFragment
import tw.north27.coachingapp.chat.ChatRoomAddViewModel
import tw.north27.coachingapp.databinding.FragmentChatRoomAddDialogBinding

class ChatRoomAddDialogFragment : BaseViewBindingBottomSheetDialogFragment<FragmentChatRoomAddDialogBinding>(FragmentChatRoomAddDialogBinding::inflate) {

    private val viewModel by sharedViewModel<ChatRoomAddViewModel>()

    private val rxPermission
        get() = RxPermissions(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomAddDialogFragment.viewModel
        }

        //camera
        binding.itemCamera.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.CAMERA, it)
            findNavController().navigateUp()
        }

        //photo
        binding.itemPhoto.linearLayoutItem.clicks().compose(
            rxPermission.ensure(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ).subscribeWithRxLife {
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.PHOTO, it)
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
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.MIC, it)
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
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.AUDIO, it)
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
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.VIDEO, it)
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
            viewModel.request(ChatRoomAddViewModel.ChatRoomAddFeature.MOVIE, it)
            findNavController().navigateUp()
        }

    }


}
