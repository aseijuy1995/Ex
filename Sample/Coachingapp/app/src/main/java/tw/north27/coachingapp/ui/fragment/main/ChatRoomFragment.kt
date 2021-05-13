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
import com.hw.videoprocessor.VideoProcessor
import com.hw.videoprocessor.util.VideoProgressListener
import com.jakewharton.rxbinding4.recyclerview.scrollStateChanges
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
import tw.north27.coachingapp.ext2.*
import tw.north27.coachingapp.media.mediaStore.Media
import tw.north27.coachingapp.media.mediaStore.MimeType
import tw.north27.coachingapp.model.result.*
import tw.north27.coachingapp.util2.SnackbarUtil
import tw.north27.coachingapp.util2.ViewState
import java.io.File
import java.util.*

class ChatRoomFragment : BaseFragment(R.layout.fragment_chat_room) {

    private val binding by viewBinding<FragmentChatRoomBinding>(FragmentChatRoomBinding::bind)

    private val viewModel by viewModel<ChatRoomViewModel>()

    private lateinit var adapter: ChatRoomListAdapter

    private val chat: ChatInfo
        get() = arguments?.getParcelable<ChatInfo>("chat")!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ChatRoomFragment.viewModel
        }
        viewModel.setChatRoomChat(chat)
        adapter = ChatRoomListAdapter(cxt, viewLifecycleOwner)
        binding.rvChat.apply {
            adapter = this@ChatRoomFragment.adapter
            scrollStateChanges().observe(viewLifecycleOwner) {
                when (it) {
                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this).resumeRequests()
                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this).pauseRequests()
                    RecyclerView.SCROLL_STATE_SETTLING -> Glide.with(this).pauseRequests()
                }
            }
        }
        viewModel.loadChatList(chat)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.chatListState.collect {
                binding.rvChat.isVisible = it is ViewState.Data
                if (it is ViewState.Data) {
                    val list = it.data
                    adapter.submitList(list)
                    /**
                     * FIXME 滑動至未讀位置
                     * */
                    viewModel.roomScrollToBottom(true)
                }
                binding.itemEmpty.root.isVisible = it is ViewState.Empty
                binding.itemError.root.isVisible = it is ViewState.Error
                binding.itemNetwork.root.isVisible = it is ViewState.Network
            }
        }
        viewModel.message.observe(viewLifecycleOwner) {
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
                Timber.d("positionStart = $positionStart, itemCount = $itemCount")
                viewModel.roomScrollToBottom(true)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                binding.rvChat.smoothScrollToPosition(itemCount)
            }
        }

        //接收通知滑動置頂
        binding.rvChat.adapter?.registerAdapterDataObserver(adapterDataObserver)

        binding.ivBack.clickThrottleFirst().observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.ivTask.clickThrottleFirst().observe(viewLifecycleOwner) {

        }

        binding.itemBottomChatRoom.etText.textChanges().observe(viewLifecycleOwner) {
            viewModel.inputEmpty(TextUtils.isEmpty(it.trim()))
        }

        binding.itemBottomChatRoom.ivAdd.clickThrottleFirst().observe(viewLifecycleOwner) {
//            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomAddDialog())
        }

        binding.itemBottomChatRoom.ivSend.clickThrottleFirst().observe(viewLifecycleOwner) {
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
            act.hideKeyBoard()
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
//                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.IMAGE))
                        }
                        ChatRoomAddViewModel.MediaFeature.MIC -> {

                        }
                        ChatRoomAddViewModel.MediaFeature.AUDIO -> {
//                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.AUDIO))
                        }
                        ChatRoomAddViewModel.MediaFeature.VIDEO -> {

                        }
                        ChatRoomAddViewModel.MediaFeature.MOVIE -> {
//                            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentChatRoomMediaDialog(MimeType.VIDEO))
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
                    var chat: ChatInfo? = null
                    when (mimeType) {
                        MimeType.AUDIO -> {

                            val chatAudioList = mutableListOf<ChatAudio>()
                            mediaList.forEach {
                                val inputPath = it.data
                                val pcmFile = File("/storage/emulated/0/Recordings/AUDIO_${System.nanoTime()}.mp3")
                                val outputPath = pcmFile.absolutePath
                                //
//                                AudioCompressModule.Compress().setCompress(
//                                    CodecSetting(
//                                        inputPath = inputPath,
//                                        outputPath = outputPath,
//                                        mimeType = MediaFormat.MIMETYPE_AUDIO_AAC
//                                    )
//                                ).compress()
//                                chatAudioList.add(ChatAudio(id = 0, url = outputPath))
                                chatAudioList.add(ChatAudio(id = 0, url = inputPath))
                            }
                            chat = ChatInfo(
                                id = 5,
                                sender = UserInfo(
                                    id = 0,
                                    account = "north27",
                                    avatarPath = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939",
                                    name = "北緯科技"
                                ),
                                recipient = UserInfo(
                                    id = 100,
                                    account = "google",
                                    avatarPath = "data:image/png;base64,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",
                                    name = "Google"
                                ),
                                sendTime = Calendar.getInstance().getDateTime("HH:mm"),
                                chatType = ChatType.AUDIO,
                                audios = chatAudioList,
                                read = ChatRead.UN_READ
                            )
                        }
                        MimeType.IMAGE -> {
                            val imgByteArray = viewModel.compressedImg(mediaList)
                            val chatImageList = mutableListOf<ChatImage>()
                            imgByteArray.map { chatImageList.add(ChatImage(id = 0, byteArray = it)) }
                            chat = ChatInfo(
                                id = 5,
                                sender = UserInfo(
                                    id = 0,
                                    account = "north27",
                                    avatarPath = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939",
                                    name = "北緯科技"
                                ),
                                recipient = UserInfo(
                                    id = 100,
                                    account = "google",
                                    avatarPath = "data:image/png;base64,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",
                                    name = "Google"
                                ),
                                sendTime = Calendar.getInstance().getDateTime("HH:mm"),
                                chatType = ChatType.IMAGE,
                                image = chatImageList,
                                read = ChatRead.UN_READ
                            )
                        }
                        MimeType.VIDEO -> {
                            val chatVideoList = mutableListOf<ChatVideo>()
                            mediaList.map {
                                val inputPath = it.data
                                val yuvFile = File("/storage/emulated/0/DCIM/Camera/YUV_${System.nanoTime()}.mp4")
                                val outputPath = yuvFile.absolutePath
                                //
                                val file = File(outputPath)
                                if (!file.parentFile.exists())
                                    file.parentFile.mkdirs()
                                if (!file.exists())
                                    file.createNewFile()
                                //
                                VideoProcessor.processor(context)
                                    .input(inputPath)
                                    .output(outputPath)
                                    .bitrate(2560 * 1000)//输出视频比特率
                                    .frameRate(25)   //帧率
                                    .iFrameInterval(1)  //关键帧距，为0时可输出全关键帧视频（部分机器上需为-1）
                                    .progressListener(VideoProgressListener {
                                        Timber.d("it = $it")
                                    }).process()
                                chatVideoList.add(ChatVideo(id = 0, url = outputPath, time = 0))
                            }
                            chat = ChatInfo(
                                id = 5,
                                sender = UserInfo(
                                    id = 0,
                                    account = "north27",
                                    avatarPath = "http://static.104.com.tw/b_profile/cust_picture/8063/130000000158063/logo.png?v=20210220092939",
                                    name = "北緯科技"
                                ),
                                recipient = UserInfo(
                                    id = 100,
                                    account = "google",
                                    avatarPath = "data:image/png;base64,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",
                                    name = "Google"
                                ),
                                sendTime = Calendar.getInstance().getDateTime("HH:mm"),
                                chatType = ChatType.VIDEO,
                                videos = chatVideoList,
                                read = ChatRead.UN_READ
                            )

                        }
                    }
                    chat?.let { viewModel.send(it) }
                }
            }
        }

        adapter.imageClickRelay?.observe(viewLifecycleOwner) {
//            findNavController().navigate(ChatRoomFragmentDirections.actionFragmentChatRoomToFragmentMediaPhoto(it.second.url.toString()))
        }

        adapter.videoClickRelay?.observe(viewLifecycleOwner) {

        }
    }

}