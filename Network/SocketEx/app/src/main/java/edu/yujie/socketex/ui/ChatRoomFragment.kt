package edu.yujie.socketex.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.adapter.ChatListAdapter
import edu.yujie.socketex.adapter.InfoListAdapter
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.bean.ChatSender
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.databinding.FragmentChatRoomBinding
import edu.yujie.socketex.finish.base.fragment.BaseDataBindingFragment
import edu.yujie.socketex.finish.vm.ChatRoomViewModel
import edu.yujie.socketex.finish.vm.MediaViewModel
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatRoomFragment : BaseDataBindingFragment<FragmentChatRoomBinding>(R.layout.fragment_chat_room) {

    private val chatRoomViewModel by sharedViewModel<ChatRoomViewModel>()

    private val mediaViewModel by sharedViewModel<MediaViewModel>()

    private val infoListAdapter = InfoListAdapter()

    private val chatListAdapter = ChatListAdapter()

    private fun initView() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.chatRoomViewModel
            rvInfo.adapter = infoListAdapter
            rvChat.apply {
                adapter = chatListAdapter
                scrollStateChanges().subscribeWithLife {
                    when (it) {
                        RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this).resumeRequests()
                        RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this).pauseRequests()
                    }
                }
            }
        }
    }

    private fun refreshInfo(infoList: List<String>) {
        infoListAdapter.submitList(infoList)
        infoListAdapter.notifyItemInserted(infoListAdapter.itemCount - 1)
        binding.rvInfo.postDelayed({
            binding.rvInfo.smoothScrollToPosition(infoListAdapter.itemCount - 1)
        }, 500)
    }

    private fun refreshChat(chatList: List<ChatItem>) {
        chatListAdapter.submitList(chatList)
        chatListAdapter.notifyItemInserted(chatListAdapter.itemCount - 1)
        binding.rvChat.postDelayed({
            binding.rvChat.smoothScrollToPosition(chatListAdapter.itemCount - 1)
        }, 500)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        clickEvent()

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
        //
        //
        //
        //recording
        chatRoomViewModel.recordingResultRelay.subscribeWithLife { (isDone, result) ->
            if (isDone) {
                result?.let {
                    chatRoomViewModel.sendRecording(result)
                }
            }
        }
        //
        //
        //

        chatRoomViewModel.infoList.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                refreshInfo(it)
            } else {

            }
        }

        chatRoomViewModel.chatItemList.observe(viewLifecycleOwner) {
            if (it.size > 0) {
                if (it.last().sender == ChatSender.OWNER) {
                    binding.includeInputBar.etText.setText("")
                    binding.rvChat.itemAnimator = SlideInRightAnimator()
//                    binding.rvChat.itemAnimator = OvershootInRightAnimator()
                } else {
                    binding.includeInputBar.etText.setText("")
                    binding.rvChat.itemAnimator = SlideInLeftAnimator()
//                    binding.rvChat.itemAnimator = OvershootInLeftAnimator()
                }
                refreshChat(it)
            }
        }


        //--------------------------------------------------------------------------------------


        mediaViewModel.toast.observe(viewLifecycleOwner) { if (it.trim().isNotEmpty()) Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).setAnchorView(binding.includeInputBar.root).show() }

        //


        //
        //item img click
        chatListAdapter.itemImgClickRelay.subscribeWithLife {
            println("itemImgClickRelay-itemImgClickRelay")
//            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPreview(it, From.IMAGE))
        }
        //item recording check
        chatListAdapter.itemRecordingClickRelay.subscribeWithLife {
            if (it.first)
                chatRoomViewModel.startRecordingPlayer(it.second)
            else
                chatRoomViewModel.stopRecordingPlayer()
        }
//
        //
        //
        //
        //
        //camera - send img
        chatRoomViewModel.cameraLiveData.observe(viewLifecycleOwner) {
            it.uris?.first()?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    chatRoomViewModel.socketViewEvent.send(SocketViewEvent.SendImg(listOf(it)))
                }
            }
        }
    }

    private fun clickEvent() {
        //inputBox
        binding.includeInputBar.etText.textChanges().subscribeWithLife {
            chatRoomViewModel.setIsInputEmpty(TextUtils.isEmpty(it.trim()))
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
        //view state
        //camera
        chatRoomViewModel.camera.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentCameraX())
        }
        //album
        chatRoomViewModel.album.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaListDialog(MimeType.IMAGE))
        }
        //recording
        chatRoomViewModel.recording.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentRecordingDialog())
        }
        //audio
        chatRoomViewModel.audio.observe(viewLifecycleOwner) {
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