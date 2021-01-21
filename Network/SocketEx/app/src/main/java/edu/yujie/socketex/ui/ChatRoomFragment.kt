package edu.yujie.socketex.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.adapter.ChatListAdapter
import edu.yujie.socketex.adapter.InfoListAdapter
import edu.yujie.socketex.base.BaseFragment
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentChatRoomBinding
import edu.yujie.socketex.util.closeKeyBoard
import edu.yujie.socketex.vm.ChatRoomViewModel
import edu.yujie.socketex.vm.MediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_room

    private val chatRoomViewModel by sharedViewModel<ChatRoomViewModel>()

    private val mediaViewModel by sharedViewModel<MediaViewModel>()

    private val infoListAdapter = InfoListAdapter()

    private val chatListAdapter = ChatListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelFlow()
        //inputBox
        binding.includeInputBar.etText
            .textChanges()
            .subscribeWithLife {
                chatRoomViewModel.setInput(TextUtils.isEmpty(it.trim()))
            }
        //add
        binding.includeInputBar.ivAdd
            .clicks()
            .subscribeWithLife {
                findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentAddBottomSheetDialog())
            }
        //send text
        binding.includeInputBar.ivSend
            .clicks()
            .subscribeWithLife {
                val text = binding.includeInputBar.etText.text.toString().trim()
                lifecycleScope.launch(Dispatchers.IO) {
                    chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendText(text))
                }
            }
        //camera - send img
        chatRoomViewModel.cameraLiveData.observe(viewLifecycleOwner) {
            it.uris?.first()?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendImg(listOf(it)))
                }
            }
        }
        //album - send img
        mediaViewModel.mediaListRelay.subscribeWithLife {
            val imgPaths = it.map { it.data }
            lifecycleScope.launch(Dispatchers.IO) {
                chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendImgPath(imgPaths))
            }
        }

//        chatRoomViewModel.albumLiveData.observe(viewLifecycleOwner) {
////            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaBottomSheetDialog(MimeType.IMAGE))
//            it.uris?.let {
//                lifecycleScope.launch(Dispatchers.IO) {
//                    chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendImg(it))
//                }
//            }
//        }
        //album
        chatRoomViewModel.album.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaBottomSheetDialog(MimeType.IMAGE))
        }
        //mic
        chatRoomViewModel.mic.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMicBottomSheetDialog())
        }
        //video
        chatRoomViewModel.video.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaBottomSheetDialog(MimeType.VIDEO))
        }
        //alert
        mediaViewModel.toastRelay.subscribeWithLife {
            if (it.trim().isNotEmpty())
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show()
        }

        //
        //uri - start webSocket
        chatRoomViewModel.webSocketUrl.observe(viewLifecycleOwner) { chatRoomViewModel.startWebSocket(it) }
        //info list
        chatRoomViewModel.infoListLiveData.observe(viewLifecycleOwner) { refreshInfo(it) }
        //chat list
        chatRoomViewModel.chatListLiveData.observe(viewLifecycleOwner) { refreshChat(it) }

        //toast
        chatRoomViewModel.toastLiveData.observe(viewLifecycleOwner) { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        //
    }

    private fun initView() {
        binding.rvInfo.adapter = infoListAdapter
        binding.rvChat.adapter = chatListAdapter
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.chatRoomViewModel
        }
    }

    private fun viewModelFlow() {
        lifecycleScope.launch {
            chatRoomViewModel.startMockServer().collect {
                when (it) {
                    //server
                    is SocketState.onServerOpen -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onServerMessage -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onServerClosing -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onServerClosed -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onServerFailure -> {
                        chatRoomViewModel.addInfo(it.msg)
                        Snackbar.make(binding.rvInfo, "Server onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show()
                    }
                    //client
                    is SocketState.onClientOpen -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onClientMessage -> {
                        chatRoomViewModel.addInfo(it.msg)
                        chatRoomViewModel.addChat(it.chatItem)
                    }
                    is SocketState.onClientClosing -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onClientClosed -> chatRoomViewModel.addInfo(it.msg)
                    is SocketState.onClientFailure -> {
                        chatRoomViewModel.addInfo(it.msg)
                        Snackbar.make(binding.rvInfo, "Client onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show()
                    }
                    //chat
                    is SocketState.ShowChat -> {
                        binding.includeInputBar.etText.setText("")//clean on send success
                        chatRoomViewModel.addChat(it.chatItem)
                    }
                }
            }
        }
    }

    private fun refreshInfo(infoList: List<String>) {
        infoListAdapter.submitList(infoList)

        binding.rvInfo.scrollToPosition(infoList.size)
        binding.rvInfo.postDelayed({
            binding.rvInfo.smoothScrollToPosition(infoList.size)
        }, 50)
    }

    private fun refreshChat(chatList: List<ChatItem>) {
        requireContext().closeKeyBoard(binding.includeInputBar.ivSend)
        chatListAdapter.submitList(chatList)

        binding.rvChat.scrollToPosition(chatList.size)
        binding.rvChat.postDelayed({
            binding.rvChat.smoothScrollToPosition(chatList.size)
        }, 50)
    }


}