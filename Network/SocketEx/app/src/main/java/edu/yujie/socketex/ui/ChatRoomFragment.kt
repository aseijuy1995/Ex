package edu.yujie.socketex.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.ChatRoomViewModel
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.FragmentChatRoomBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_chat_room

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeFeaturesBar.ivAdd.clicks().subscribe {
            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomAddDialog())
        }

        //capture
        viewModel.captureUrlLiveData.observe(viewLifecycleOwner) {
            val stream = requireActivity().contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(stream)
        }

        //album
        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            it.forEach {
                val stream = requireActivity().contentResolver.openInputStream(it)
                val bitmap = BitmapFactory.decodeStream(stream)
            }
        }
        //Mic Display
        viewModel.micDisplayLiveData.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMicBottomSheetDialog())
            }
        }
    }


}