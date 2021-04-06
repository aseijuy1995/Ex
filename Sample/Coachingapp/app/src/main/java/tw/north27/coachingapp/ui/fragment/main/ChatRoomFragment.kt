package tw.north27.coachingapp.ui.fragment.main

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseFragment
import tw.north27.coachingapp.chat.*
import tw.north27.coachingapp.databinding.FragmentChatRoomBinding
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.media.RecorderSetting
import tw.north27.coachingapp.model.result.*
import tw.north27.coachingapp.util.SnackbarUtil
import tw.north27.coachingapp.util.ViewState

class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val binding by viewBinding<FragmentChatRoomBinding>(FragmentChatRoomBinding::bind)

    private val viewModel by viewModel<ChatRoomViewModel>()

    private lateinit var adapter: ChatRoomListAdapter

    private val chat: ChatInfo
        get() = arguments?.getParcelable<ChatInfo>("chat")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setChat(chat)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.viewModel
        }
        adapter = ChatRoomListAdapter(cxt)
        binding.rvChat.apply {
            adapter = this@ChatRoomFragment.adapter
            scrollStateChanges().subscribeWithRxLife {
                when (it) {
                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this).resumeRequests()
                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this).pauseRequests()
                }
            }
        }
        viewModel.loadChatListFromChat(chat)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.loadChatState.collect {
//                if (it is ViewState.Load) showLoadingDialog()
//                else dismissLoadingDialog()

                binding.rvChat.isVisible = it is ViewState.Data
                if (it is ViewState.Data) {
                    val list = it.data
                    adapter.submitList(list)
                    viewModel.roomScrollToBottom(true)
                }
                binding.itemEmpty.clEmpty.isVisible = it is ViewState.Empty
                binding.itemError.clError.isVisible = it is ViewState.Error
                binding.itemNetwork.clNetwork.isVisible = it is ViewState.Network
            }
        }

        viewModel.message.subscribeWithRxLife {
            viewModel.addChat(it)
        }

        viewModel.roomScrollToBottom.observe(viewLifecycleOwner) {
            if (binding.rvChat.size > 0) {
                val position = adapter.currentList.size - 1
                binding.rvChat.scrollToPosition(position)
                binding.rvChat.postDelayed({
                    binding.rvChat.smoothScrollToPosition(position)
                }, 500)
            }
        }

        val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                viewModel.roomScrollToBottom(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(adapterDataObserver)

        binding.ivBack.clickThrottleFirst()
            .subscribeWithRxLife {
                findNavController().navigateUp()
            }

        /**
         * 詳情頁面
         * */
        binding.ivDehaze.clicks().subscribeWithRxLife {

        }

        binding.clChatRoom.clicks().subscribeWithRxLife {
            cxt.hideKeyBoard(binding.clChatRoom)
        }

        binding.itemBottomChatRoom.etText.textChanges().subscribeWithRxLife {
            viewModel.inputEmpty(TextUtils.isEmpty(it.trim()))
        }

        binding.itemBottomChatRoom.ivAdd.clicks().subscribeWithRxLife {
            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomAddDialog())
        }

        binding.itemBottomChatRoom.ivSend.clicks().subscribeWithRxLife {
            val text = binding.itemBottomChatRoom.etText.text.toString()
            viewModel.send(
                ChatInfo(
                    id = 5,
                    sender = UserInfo(
                        id = -1,
                        account = "north27",
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
            binding.itemBottomChatRoom.etText.setText("")
            cxt.hideKeyBoard(binding.clChatRoom)
        }

        setFragmentResultListener(ChatRoomAddDialogFragment.REQUEST_KEY_CHAT_ROOM_ADD) { key, bundle ->
            val isPer: Boolean = bundle.getBoolean(ChatRoomAddDialogFragment.KEY_MEDIA_PERMISSION)
            val feature: ChatRoomAddViewModel.MediaFeature? = bundle.getParcelable<ChatRoomAddViewModel.MediaFeature>(ChatRoomAddDialogFragment.KEY_MEDIA_FEATURE)
            if (isPer) {
                /**
                 * FIXME 延遲以防止JetPack-Navigation觸發IllegalArgumentException
                 * */
                lifecycleScope.launch {
                    delay(500)
                    when (feature) {
                        ChatRoomAddViewModel.MediaFeature.CAMERA -> {
//                        if (it) findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentCameraX())
                        }
                        ChatRoomAddViewModel.MediaFeature.PHOTO -> {
                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.IMAGE))
                        }
                        ChatRoomAddViewModel.MediaFeature.MIC -> {

                        }
                        ChatRoomAddViewModel.MediaFeature.AUDIO -> {
                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.AUDIO))
                        }
                        ChatRoomAddViewModel.MediaFeature.VIDEO -> {

                        }
                        ChatRoomAddViewModel.MediaFeature.MOVIE -> {
                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.VIDEO))
                        }
                    }

                }
            } else {
                SnackbarUtil.showPermissionDeny(binding.root)
            }
        }

        setFragmentResultListener(ChatRoomMediaDialogFragment.REQUEST_KEY_MEDIA) { key, bundle ->
            lifecycleScope.launch(Dispatchers.IO) {
                val mimeType: MimeType? = bundle.getParcelable<MimeType>(ChatRoomMediaDialogFragment.KEY_MIME_TYPE)
                val mediaList: List<Media>? = bundle.getParcelableArrayList<Media>(ChatRoomMediaDialogFragment.KEY_MEDIA_LIST)
                if (!mediaList.isNullOrEmpty()) {
                    var chatInfo: ChatInfo? = null
                    when (mimeType) {
                        MimeType.AUDIO -> {
                            val media = mediaList.first()

                            val setting = RecorderSetting(cxt)
                            Timber.d("RecorderSetting:file = ${setting.file}")
                            viewModel.startRecording(setting)
//                            val pcmPath = viewModel.audioDecodeToPcm(media.data)
//                            chatInfo = ChatInfo(
//                                id = 5,
//                                sender = UserInfo(
//                                    id = -1,
//                                    account = "north27",
//                                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                                    name = "阿吉"
//                                ),
//                                recipient = UserInfo(
//                                    id = 100,
//                                    account = "north27",
//                                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                                    name = "阿吉 - 測試號"
//                                ),
//                                sendTime = "15:00",
//                                chatType = ChatType.AUDIO,
//                                audios = ChatAudio(
//                                    1,
//                                    pcmPath,
//
//                                )
//                                read = ChatRead.UN_READ,
//                                unReadCount = 1,
//                                isSound = true
//                            )
                        }
                        MimeType.IMAGE -> {
                            val imgByteArray = viewModel.compressedImg(mediaList)
                            val chatImageList = mutableListOf<ChatImage>()
                            imgByteArray.map { chatImageList.add(ChatImage(id = 0, byteArray = it)) }
                            chatInfo = ChatInfo(
                                id = 5,
                                sender = UserInfo(
                                    id = -1,
                                    account = "north27",
                                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
                                    name = "阿吉"
                                ),
                                recipient = UserInfo(
                                    id = 100,
                                    account = "north27",
                                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
                                    name = "阿吉 - 測試號"
                                ),
                                sendTime = "15:00",
                                chatType = ChatType.IMAGE,
                                image = chatImageList,
                                read = ChatRead.UN_READ,
                                unReadCount = 1,
                                isSound = true
                            )

                        }
                        MimeType.VIDEO -> {
                            val media = mediaList.first()
                            Timber.d("video: media = ${media}")
                            Timber.d("video: media.data = ${media.data}")
//                            viewModel.audioDecodeToPcm(media.data)

//                            val videoByteArray = viewModel.compressedImg(mediaList)
//                            val chatVideoList = mutableListOf<ChatVideo>()
//                            videoByteArray.map { chatVideoList.add(ChatVideo(id = 0, byteArray = it, time = 0)) }
//                            chatInfo = ChatInfo(
//                                id = 5,
//                                sender = UserInfo(
//                                    id = -1,
//                                    account = "north27",
//                                    avatarPath = "https://memes.tw/user-template-thumbnail/7c1c504fb55e5012dbc4e4c5a372cb4e.jpg",
//                                    name = "阿吉"
//                                ),
//                                recipient = UserInfo(
//                                    id = 100,
//                                    account = "ji100",
//                                    avatarPath = "https://lh3.googleusercontent.com/proxy/J6HSb3iafP23kEvTrB4TVG7mqwLl_Jl-Y1h2GnHGzRit1Mv-RwT0gxp0PapQO5YWAlkBtMepmVjdmV3XseUlN1qR_mdzEoBvUuAW27Jd5znM_AZI7_qSeruT",
//                                    name = "阿吉 - 測試號"
//                                ),
//                                sendTime = "15:00",
//                                chatType = ChatType.VIDEO,
//                                videos = chatVideoList,
//                                read = ChatRead.UN_READ,
//                                unReadCount = 1,
//                                isSound = true
//                            )

                        }
                    }
//                    chatInfo?.let { viewModel.send(it) }
                }
            }
        }

        adapter.imageClickRelay?.subscribeWithRxLife {
            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.url.toString()))
        }

        viewModel.toast.observe(viewLifecycleOwner, ::onToastObs)
    }

    private fun onToastObs(pair: Pair<ChatRoomViewModel.ToastType, String>) {
        when (pair.first) {
            ChatRoomViewModel.ToastType.LOAD_CHAT_MESSAGE_LIST -> {
                Snackbar.make(binding.rvChat, pair.second, Snackbar.LENGTH_SHORT).show()
            }
        }
    }


}