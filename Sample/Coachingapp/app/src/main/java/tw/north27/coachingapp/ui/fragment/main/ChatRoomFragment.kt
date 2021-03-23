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
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.ChatRoomListAdapter
import tw.north27.coachingapp.chat.ChatRoomViewModel
import tw.north27.coachingapp.databinding.FragmentChatRoomBinding
import tw.north27.coachingapp.ext.closeKeyBoard
import tw.north27.coachingapp.ext.viewBinding
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead
import tw.north27.coachingapp.model.result.ChatType
import tw.north27.coachingapp.model.result.UserInfo

class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val binding by viewBinding<FragmentChatRoomBinding>(FragmentChatRoomBinding::bind)

    private val viewModel by viewModel<ChatRoomViewModel>()

    private lateinit var chatRoomListAdapter: ChatRoomListAdapter

    private val chat: ChatInfo
        get() = arguments?.getParcelable<ChatInfo>("chat")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.viewModel
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
        viewModel.chatMessageList(chat)

        viewModel.chatList.observe(viewLifecycleOwner) {
            chatRoomListAdapter.submitList(it)
            if (it.isNotEmpty())
                viewModel.roomScrollToBottom(true)
        }

        viewModel.message.subscribeWithRxLife {
            viewModel.addChat(it)
        }

        viewModel.roomScrollToBottom.observe(viewLifecycleOwner) {
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
                viewModel.roomScrollToBottom(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        })

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)

        binding.ivBack.clicks().subscribeWithRxLife {
            findNavController().navigateUp()
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
            viewModel.inputEmpty(TextUtils.isEmpty(it.trim()))
        }
        //add
        binding.itemBottomChatRoom.ivAdd.clicks().subscribeWithRxLife {
//            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentAddDialog())
        }
        //send text
        binding.itemBottomChatRoom.ivSend.clicks().subscribeWithRxLife {
            val text = binding.itemBottomChatRoom.etText.text.toString()
            viewModel.send(
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