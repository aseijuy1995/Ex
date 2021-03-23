package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.size
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatRoomAddViewModel
import tw.north27.coachingapp.chat.ChatRoomListAdapter
import tw.north27.coachingapp.chat.ChatRoomViewModel
import tw.north27.coachingapp.databinding.FragmentChatRoomBinding
import tw.north27.coachingapp.ext.closeKeyBoard
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead
import tw.north27.coachingapp.model.result.ChatType
import tw.north27.coachingapp.model.result.UserInfo
import tw.north27.coachingapp.util.SnackbarUtil

class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val binding by viewBinding<FragmentChatRoomBinding>(FragmentChatRoomBinding::bind)

    private val chatRoomViewModel by viewModel<ChatRoomViewModel>()

    private val chatRoomAddViewModel by sharedViewModel<ChatRoomAddViewModel>()

    private lateinit var chatRoomListAdapter: ChatRoomListAdapter

    private val chat: ChatInfo
        get() = arguments?.getParcelable<ChatInfo>("chat")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.chatRoomViewModel
            chat = this@ChatRoomFragment.chat
        }
        chatRoomListAdapter = ChatRoomListAdapter(cxt)
        binding.rvChat.apply {
            adapter = chatRoomListAdapter
            scrollStateChanges().subscribeWithRxLife {
                when (it) {
                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this).resumeRequests()
                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this).pauseRequests()
                }
            }
        }
        chatRoomViewModel.chatMessageList(chat)

        chatRoomViewModel.chatList.observe(viewLifecycleOwner) {
            chatRoomListAdapter.submitList(it)
            if (it.isNotEmpty())
                chatRoomViewModel.roomScrollToBottom(true)
        }

        chatRoomViewModel.message.subscribeWithRxLife {
            chatRoomViewModel.addChat(it)
        }

        chatRoomViewModel.roomScrollToBottom.observe(viewLifecycleOwner) {
            if (binding.rvChat.size > 0) {
                val position = chatRoomListAdapter.currentList.size - 1
                binding.rvChat.scrollToPosition(position)
                binding.rvChat.postDelayed({
                    binding.rvChat.smoothScrollToPosition(position)
                }, 500)
            }
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                chatRoomViewModel.roomScrollToBottom(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        })

        chatRoomViewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

        binding.ivBack.clicks().subscribeWithRxLife {
            findNavController().navigateUp()
        }

        chatRoomAddViewModel.request.observe(viewLifecycleOwner) {
            val feature = it.first
            val isRequest = it.second
            if (isRequest) {
                when (feature) {
                    ChatRoomAddViewModel.ChatRoomAddFeature.CAMERA -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentCameraX())
                    }
                    ChatRoomAddViewModel.ChatRoomAddFeature.PHOTO -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.IMAGE))
                    }
                    ChatRoomAddViewModel.ChatRoomAddFeature.MIC -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentRecordingDialog())
                    }
                    ChatRoomAddViewModel.ChatRoomAddFeature.AUDIO -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentRecordingDialog())
                    }
                    ChatRoomAddViewModel.ChatRoomAddFeature.VIDEO -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.VIDEO))
                    }
                    ChatRoomAddViewModel.ChatRoomAddFeature.MOVIE -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.VIDEO))
                    }
                }
            } else {
                SnackbarUtil.showPermissionDeny(binding.root)
            }
        }

        /**
         * 詳情頁面
         * */
        binding.ivDehaze.clicks().subscribeWithRxLife {

        }

        binding.rvChat.clicks().subscribeWithRxLife {
            cxt.closeKeyBoard(binding.rvChat)
        }

        binding.itemBottomChatRoom.etText.textChanges().subscribeWithRxLife {
            chatRoomViewModel.inputEmpty(TextUtils.isEmpty(it.trim()))
        }
        //add
        binding.itemBottomChatRoom.ivAdd.clicks().subscribeWithRxLife {
            Timber.d("itemBottomChatRoom")
            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomAddDialog())
        }
        //send text
        binding.itemBottomChatRoom.ivSend.clicks().subscribeWithRxLife {
            val text = binding.itemBottomChatRoom.etText.text.toString()
            chatRoomViewModel.send(
                ChatInfo(
                    id = 5,
                    sender = UserInfo(
                        id = -1,
                        account = "jie001",
                        avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                        name = "阿吉"
                    ),
                    recipient = UserInfo(
                        id = 100,
                        account = "ji100",
                        avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                        name = "阿吉 - 測試號"
                    ),
                    sendTime = "15:00",
                    chatType = ChatType.TEXT,
                    text = text,
                    read = ChatRead.UN_READ,
                    unReadCount = 1,
                    isSound = true
                )
            )
        }

    }

    private fun onToastObs(pair: Pair<ChatRoomViewModel.ToastType, String>) {
        when (pair.first) {
            ChatRoomViewModel.ToastType.LOAD_CHAT_MESSAGE_LIST -> {
                Snackbar.make(binding.rvChat, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}