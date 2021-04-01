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
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.UserModule
import tw.north27.coachingapp.util.ViewState
import java.io.File

class ChatRoomViewModel(application: Application, val chatRepo: IChatRepository, val mediaRepo: IMediaRepository) : BaseAndroidViewModel(application), KoinComponent {

    private val _chat = MutableLiveData<ChatInfo>()

    val chat = _chat.asLiveData()

    fun setChat(chat: ChatInfo) {
        _chat.value = chat
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private val _loadChatState = MutableStateFlow<ViewState<List<ChatInfo>>>(ViewState.Load)

    val loadChatState = _loadChatState.asStateFlow()

    fun loadChatListFromChat(chat: ChatInfo) {
        viewModelScope.launch {
            _loadChatState.value = ViewState.load()
            val results = chatRepo.loadChatListFromChat(chat)
            when (results) {
                is Results.Successful -> {
                    val list = results.data
                    _loadChatState.value = if (list.isNullOrEmpty()) ViewState.empty()
                    else ViewState.data(list)
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "初始完成")
                }
                is Results.ClientErrors -> {
                    _loadChatState.value = ViewState.error(results.e)
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "${results.e}:無法獲取初始數據")
                }
                is Results.NetWorkError -> {
                    _loadChatState.value = ViewState.network(results.e)
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "${results.e}:網路異常")
                }
            }
        }
    }

    fun addChat(chat: ChatInfo) {
        viewModelScope.launch {
            var list: MutableList<ChatInfo> = mutableListOf()
            if (loadChatState.value is ViewState.Data) {
                list = (loadChatState.value as ViewState.Data).data.map { it.copy() }.toMutableList()
            }
            list.add(chat)
            _loadChatState.value = ViewState.data(list)
        }
    }

    fun send(chat: ChatInfo) {
        viewModelScope.launch {
            chatRepo.webSocket?.let {
                var list: MutableList<ChatInfo> = mutableListOf()
                if (loadChatState.value is ViewState.Data) {
                    list = (loadChatState.value as ViewState.Data).data.map { it.copy() }.toMutableList()
                }
                list.add(chat)
                _loadChatState.value = ViewState.data(list)
                chatRepo.send(it, chat)
            } ?: _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "連線已被中斷")
        }
    }

    private val _inputEmpty = MutableLiveData<Boolean>(true)

    val inputEmpty = _inputEmpty.asLiveData()

    val inputRes = _inputEmpty.map {
        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    fun inputEmpty(isInputEmpty: Boolean) {
        _inputEmpty.value = isInputEmpty
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun audioDecodeToPcm(filePath: String):String {
        val pcmPath = "${context.externalCacheDir}/Champagne_Edition_2.pcm"
        viewModelScope.launch(Dispatchers.IO) {
            mediaRepo.audioDecodeToPcm(filePath, pcmPath)
        }
        return pcmPath
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
    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        LOAD_CHAT_MESSAGE_LIST
    }


    val message = chatRepo.message

    private val userModule = UserModule(application)

//    fun addChatMessage(chat: ChatInfo) {
//        _chatMessageList
//    }

    /**
     * 聊天室列表置底
     * */

    private val _roomScrollToBottom = MutableLiveData<Boolean>(false)

    val roomScrollToBottom = _roomScrollToBottom.asLiveData()

    fun roomScrollToBottom(isScrollToBottom: Boolean) {
        _roomScrollToBottom.value = isScrollToBottom
    }
}