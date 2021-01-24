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
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.adapter.ChatListAdapter
import edu.yujie.socketex.adapter.InfoListAdapter
import edu.yujie.socketex.base.BaseFragment
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.bean.ChatSender
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentChatRoomBinding
import edu.yujie.socketex.util.closeKeyBoard
import edu.yujie.socketex.vm.ChatRoomViewModel
import edu.yujie.socketex.vm.MediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat_room

    private val chatRoomViewModel by sharedViewModel<ChatRoomViewModel>()

    private val mediaViewModel by sharedViewModel<MediaViewModel>()

    private val infoListAdapter = InfoListAdapter()

    private val chatListAdapter = ChatListAdapter()

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.chatRoomViewModel
            rvInfo.adapter = infoListAdapter
            rvChat.adapter = chatListAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //inputBox
        binding.includeInputBar.etText.textChanges().subscribeWithLife {
            chatRoomViewModel.setInput(TextUtils.isEmpty(it.trim()))
        }
        //add
        binding.includeInputBar.ivAdd.clicks().subscribeWithLife {
            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentAddDialog())
        }
        //send text
        binding.includeInputBar.ivSend.clicks().subscribeWithLife {
            val text = binding.includeInputBar.etText.text.toString()
            chatRoomViewModel.sendText(text)
        }

        //album & video- send img
        mediaViewModel.mediaListRelay.subscribeWithLife {
            if (it.first().mimeType.startsWith(MimeType.IMAGE.toString())) {
                val imgPaths = it.map { it.data }
                chatRoomViewModel.sendImg(imgPaths)

            } else if (it.first().mimeType.startsWith(MimeType.VIDEO.toString())) {
                val videoPaths = it.map { it.data }
                chatRoomViewModel.sendVideo(videoPaths)

            } else {

            }

        }

        chatRoomViewModel.infoListLiveData.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                refreshInfo(it)
            } else {

            }
        }

        chatRoomViewModel.chatListLiveData.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                if (it.last().sender == ChatSender.OWNER)
                    binding.includeInputBar.etText.setText("")
                refreshChat(it)
            } else {

            }
        }

        chatRoomViewModel.toast.observe(viewLifecycleOwner) {
            if (!TextUtils.isEmpty(it.trim())) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }

        //--------------------------------------------------------------------------------------
        //view state
        //camera
        chatRoomViewModel.camera.observe(viewLifecycleOwner) {
//            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaBottomSheetDialog(MimeType.VIDEO))
        }
        //album
        chatRoomViewModel.album.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.IMAGE))
        }
        //audio
        chatRoomViewModel.audio.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentRecordingDialog())
        }
        //recording
        chatRoomViewModel.recording.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentRecordingDialog())
        }
        //photography
        chatRoomViewModel.photography.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.VIDEO))
        }
        //video
        chatRoomViewModel.video.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.VIDEO))
        }

        mediaViewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show() }

        //


        //
        //img preview
        chatListAdapter.itemImgClickRelay.subscribeWithLife {
//            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPreview(it, From.IMAGE))
        }
        //
        //
        //

        //


        //
        //
        //
        //
        //
        //
//        lifecycleScope.launch(Dispatchers.Main) {
//            chatRoomViewModel.socketStateFlow.collect {
//                when (it) {
//                    //server
//                    is SocketState.onServerOpen -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onServerMessage -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onServerClosing -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onServerClosed -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onServerFailure -> {
//                        chatRoomViewModel.addInfo(it.msg)
//                        Snackbar.make(binding.rvInfo, "Server onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show()
//                    }
//                    //client
//                    is SocketState.onClientOpen -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onClientMessage -> {
//                        chatRoomViewModel.addInfo(it.msg)
//                        chatRoomViewModel.addChat(it.chatItem)
//                    }
//                    is SocketState.onClientClosing -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onClientClosed -> chatRoomViewModel.addInfo(it.msg)
//                    is SocketState.onClientFailure -> {
//                        chatRoomViewModel.addInfo(it.msg)
//                        Snackbar.make(binding.rvInfo, "Client onFailure()", Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show()
//                    }
//                    //chat
//                    is SocketState.ShowChat -> {
//                        binding.includeInputBar.etText.setText("")//clean on send success
//                        chatRoomViewModel.addChat(it.chatItem)
//                    }
//                }
//            }
//        }

//        //info list
//        chatRoomViewModel.infoListLiveData.observe(viewLifecycleOwner) { refreshInfo(it) }
//        //chat list
//        chatRoomViewModel.chatListLiveData.observe(viewLifecycleOwner) { refreshChat(it) }
        //

        //camera - send img
        chatRoomViewModel.cameraLiveData.observe(viewLifecycleOwner) {
            it.uris?.first()?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendImg(listOf(it)))
                }
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

        //
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewModel.onCameraResult(requestCode, resultCode, data).subscribe {
//            when (it) {
//                is IntentResult.IntentResultSuccess -> {
//                    viewModel.createCropBuilder(it.uris!!.first()) { startActivityForResult(it.intent, it.requestCode) }
//                }
//                is IntentResult.IntentResultFailed -> {
//                    Snackbar.make(binding.viewCamera.viewItem, "Please take pictures!", Snackbar.LENGTH_SHORT).setAnchorView(requireActivity().window.decorView).show()
//                    findNavController().navigateUp()
//                }
//            }
//        }
//        viewModel.onCropResult(requestCode, resultCode, data).subscribe {
//            when (it) {
//                is IntentResult.IntentResultSuccess -> {
//                    viewModel.cameraCropResultEvent(it)
//                    findNavController().navigateUp()
//                }
//                is IntentResult.IntentResultFailed -> {
//                    Snackbar.make(binding.viewCamera.viewItem, "Please Crop photo!", Snackbar.LENGTH_SHORT).setAnchorView(requireActivity().window.decorView).show()
//                    findNavController().navigateUp()
//                }
//            }
//        }
////        viewModel.onAlbumResult(requestCode, resultCode, data).subscribe {
////            when (it) {
////                is IntentResult.IntentResultSuccess -> {
////                    println("$TAG onAlbumResult")
////                    viewModel.albumResultEvent(it)
////                    findNavController().navigateUp()
////                }
////                is IntentResult.IntentResultFailed -> {
////                    Snackbar.make(binding.viewCamera.viewItem, "Please select Photo!", Snackbar.LENGTH_SHORT).setAnchorView(binding.root).show()
////                    findNavController().navigateUp()
////                }
////            }
////        }
//    }


    //
//        //camera
//        binding.viewCamera.viewItem.clicks().compose(
//            rxPermission.ensure(
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            )
//        ).subscribeWithLife {
//            if (it) {
//                viewModel.createCameraBuilder { startActivityForResult(it.intent, it.requestCode) }
//            } else {
//                Snackbar.make(binding.viewCamera.viewItem, "Permission deny!", Snackbar.LENGTH_SHORT).show()
//            }
//        }
    //
    //
    //
    //
    //
    //

}