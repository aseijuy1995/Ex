package tw.north27.coachingapp.chat

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.media.RecorderSetting
import tw.north27.coachingapp.media.mediaStore.Media
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.UserModule
import tw.north27.coachingapp.util.ViewState
import java.io.File

class ChatRoomViewModel(
    application: Application,
    val chatRepo: IChatRepository,
    val mediaRepo: IMediaRepository
) : BaseAndroidViewModel(application), KoinComponent {

    private val _chat = MutableLiveData<ChatInfo>()

    val chat = _chat.asLiveData()

    fun setChatRoomChat(chat: ChatInfo) {
        _chat.value = chat
    }

    private val _chatListState = MutableStateFlow<ViewState<List<ChatInfo>>>(ViewState.Initial)

    val chatListState = _chatListState.asStateFlow()

    fun loadChatList(chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatListState.value = ViewState.load()
            val results = chatRepo.loadChatList(chat)
            when (results) {
                is Results.Successful -> {
                    val list = results.data
                    _chatListState.value =
                        if (list.isNullOrEmpty()) ViewState.empty()
                        else ViewState.data(list)
                }
                is Results.ClientErrors -> {
                    _chatListState.value = ViewState.error(results.e)
                }
                is Results.NetWorkError -> {
                    _chatListState.value = ViewState.network(results.e)
                }
            }
        }
    }

    val message = chatRepo.message

    /**
     * 聊天室列表置底
     * */
    private val _roomScrollToBottom = MutableLiveData<Boolean>(false)

    val roomScrollToBottom = _roomScrollToBottom.asLiveData()

    fun roomScrollToBottom(isScrollToBottom: Boolean) {
        _roomScrollToBottom.value = isScrollToBottom
    }

    fun addChat(chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<ChatInfo> = if (chatListState.value is ViewState.Data) (chatListState.value as ViewState.Data).data.toMutableList() else mutableListOf()
            list.add(chat)
            _chatListState.value = ViewState.data(list)
        }
    }

    fun send(chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            /**
             * FIXME 需增加連線中斷處理機制
             * */
            chatRepo.webSocket?.let {
                addChat(chat)
                chatRepo.send(it, chat)
            }
        }
    }

    private val _inputEmpty = MutableLiveData<Boolean>(true)

    val inputEmpty = _inputEmpty.asLiveData()

    val sendRes = _inputEmpty.map {
        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    fun inputEmpty(isEmpty: Boolean) {
        _inputEmpty.value = isEmpty
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    fun audioDecodeToPcm(filePath: String):String {
//        val pcmPath = "${context.externalCacheDir}/Champagne_Edition_2.pcm"
//        viewModelScope.launch(Dispatchers.IO) {
//            mediaRepo.audioDecodeToPcm(filePath, pcmPath)
//        }
//        return pcmPath
//    }

    val recordingTime = mediaRepo.recordingTime

    fun startRecording(setting: RecorderSetting) {
        mediaRepo.prepareRecording(setting)
        mediaRepo.startRecording()
    }

    fun stopRecording() {
        mediaRepo.stopRecording()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    val bitmapOption = BitmapOption(
        isOptions = true,
        reqWidth = 400,
        reqHeight = 800
    )

    val bitmapCompress = BitmapCompress(
        isCompress = true,
        format = Bitmap.CompressFormat.PNG,
        quality = 80
    )

    /**
     * 壓縮圖片
     * */
    fun compressedImg(mediaList: List<Media>): List<ByteArray> {
//        val inputStream = File(it.data).getBitmap(bitmapOption).toInputStream(bitmapCompress)
        val imgByteArray = mediaList.map { File(it.data).getBitmap(bitmapOption).toByteArray(bitmapCompress) }
        return imgByteArray
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        LOAD_CHAT_MESSAGE_LIST
    }

    private val userModule = UserModule(application)

//    fun addChatMessage(chat: ChatInfo) {
//        _chatMessageList
//    }
}