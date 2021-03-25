package tw.north27.coachingapp.chat

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.ext.*
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.module.pref.UserModule
import java.io.File

class ChatRoomViewModel(application: Application, val chatRepo: IChatRepository) : BaseAndroidViewModel(application), KoinComponent {

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

    private val _inputEmpty = MutableLiveData<Boolean>(true)

    val inputEmpty = _inputEmpty.asLiveData()

    val inputRes = _inputEmpty.map {
        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    fun inputEmpty(isInputEmpty: Boolean) {
        _inputEmpty.value = isInputEmpty
    }

    private var chat: ChatInfo? = null

    private val _chatList = MutableLiveData<List<ChatInfo>>(mutableListOf())

    val chatList = _chatList.asLiveData()

    fun chatMessageList(chat: ChatInfo) {
        this.chat = chat
        viewModelScope.launch {
            val results = chatRepo.loadChatList()
            when (results) {
                is Results.Successful -> {
                    _chatList.postValue(results.data!!)
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "初始完成")
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "${results.e}:無法獲取初始數據")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "${results.e}:網路異常")
                }
            }
        }
    }

    val message = chatRepo.message

    fun addChat(chat: ChatInfo) {
        viewModelScope.launch {
            val chatLists = (chatList.value?.toMutableList() ?: mutableListOf<ChatInfo>()).apply { add(chat) }
            _chatList.postValue(chatLists)
        }
    }

    fun send(chat: ChatInfo) {
        viewModelScope.launch {
            chatRepo.webSocket?.let {
                val currentChatList = chatList.value?.toMutableList() ?: mutableListOf()
                currentChatList.add(chat)
                _chatList.postValue(currentChatList)
                chatRepo.send(it, chat)
            } ?: _toast.postValue(ToastType.LOAD_CHAT_MESSAGE_LIST to "連線已被中斷")
        }
    }

    private val userModule = UserModule(application)

    val chatPartnerAccount: String?
        get() {
            val account = runBlocking { userModule.getValue { it.account }.first() }
            chat?.let {
                return when (account) {
                    it.sender.account -> it.recipient.account
                    it.recipient.account -> it.sender.account
                    else -> null
                }

            } ?: return null
        }

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