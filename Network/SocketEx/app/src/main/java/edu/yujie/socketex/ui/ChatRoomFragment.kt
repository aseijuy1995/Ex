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
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.adapter.ChatListAdapter
import edu.yujie.socketex.adapter.InfoListAdapter
import edu.yujie.socketex.base.BaseFragment
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.databinding.FragmentChatRoomBinding
import edu.yujie.socketex.util.closeKeyBoard
import edu.yujie.socketex.vm.ChatRoomViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_chat_room

    private val viewModel by sharedViewModel<ChatRoomViewModel>()

    private val infoListAdapter = InfoListAdapter()

    private val chatListAdapter = ChatListAdapter()

    override fun initView() {
        binding.rvInfo.adapter = infoListAdapter
        binding.rvChat.adapter = chatListAdapter
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.viewModel
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFlow()
        //inputBox
        binding.includeFeaturesBar.etText
            .textChanges()
            .bindToLifecycle(viewLifecycleOwner)
            .subscribe {
                viewModel.inputEmptyState.value = TextUtils.isEmpty(it.trim())
            }
        //add
        binding.includeFeaturesBar.ivAdd
            .clicks()
            .bindToLifecycle(viewLifecycleOwner)
            .subscribe {
                findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomAddDialog())
            }
        //send text
        binding.includeFeaturesBar.ivSend
            .clicks()
            .bindToLifecycle(viewLifecycleOwner)
            .subscribe {
                val text = binding.includeFeaturesBar.etText.text.toString().trim()
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.socketViewEvent.send(SocketViewEvent.SendText(text))
                }
            }
        //camera - send img
        viewModel.cameraLiveData.observe(viewLifecycleOwner) {
            it.uris?.first()?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.socketViewEvent.send(SocketViewEvent.SendImg(listOf(it)))
                }
            }
        }
        //album - send img
        viewModel.albumLiveData.observe(viewLifecycleOwner) {
            it.uris?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.socketViewEvent.send(SocketViewEvent.SendImg(it))
                }
            }
        }
        //mic
        viewModel.micStateLiveData.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMicBottomSheetDialog())
        }
        //
        //uri - start webSocket
        viewModel.urlLiveData.observe(viewLifecycleOwner) { viewModel.startWebSocket(it) }
        //info list
        viewModel.infoListLiveData.observe(viewLifecycleOwner) { refreshInfo(it) }
        //chat list
        viewModel.chatListLiveData.observe(viewLifecycleOwner) { refreshChat(it) }

        //toast
        viewModel.toastLiveData.observe(viewLifecycleOwner) { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show() }
        //
    }

    private fun viewModelFlow() {
        lifecycleScope.launch {
            viewModel.startMockServer().collect {
                when (it) {
                    //server
                    is SocketState.onServerOpen -> viewModel.addInfo(it.msg)
                    is SocketState.onServerMessage -> viewModel.addInfo(it.msg)
                    is SocketState.onServerClosing -> viewModel.addInfo(it.msg)
                    is SocketState.onServerClosed -> viewModel.addInfo(it.msg)
                    is SocketState.onServerFailure -> {
                        Snackbar.make(binding.rvInfo, "Server onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeFeaturesBar.root).show()
                        viewModel.addInfo(it.msg)
                    }
                    //client
                    is SocketState.onClientOpen -> viewModel.addInfo(it.msg)
                    is SocketState.onClientMessage -> {
                        viewModel.addInfo(it.msg)
                        viewModel.addChat(it.chatItem)
                    }
                    is SocketState.onClientClosing -> viewModel.addInfo(it.msg)
                    is SocketState.onClientClosed -> viewModel.addInfo(it.msg)
                    is SocketState.onClientFailure -> {
                        Snackbar.make(binding.rvInfo, "Client onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeFeaturesBar.root).show()
                        viewModel.addInfo(it.msg)
                    }
                    //chat
                    is SocketState.ShowChat -> {
                        binding.includeFeaturesBar.etText.setText("")//clean on send success
                        viewModel.addChat(it.chatItem)
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
        requireContext().closeKeyBoard(binding.includeFeaturesBar.ivSend)
        chatListAdapter.submitList(chatList)

        binding.rvChat.scrollToPosition(chatList.size)
        binding.rvChat.postDelayed({
            binding.rvChat.smoothScrollToPosition(chatList.size)
        }, 50)
    }


}