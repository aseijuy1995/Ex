package tw.north27.coachingapp.ui.fragment.main

import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatViewModel
import tw.north27.coachingapp.databinding.FragmentChatRoomBinding
import tw.north27.coachingapp.ext.viewBinding

class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val binding by viewBinding<FragmentChatRoomBinding>(FragmentChatRoomBinding::bind)

    private val viewModel by sharedViewModel<ChatViewModel>()


}