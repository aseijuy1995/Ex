package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import java.lang.Thread.sleep

class ChatViewModel(val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        LOAD_CHAT, SWITCH_CHAT_NOTIFY
    }

    private val _loadChatState = MutableLiveData<Boolean>(true)

    val loadChatState = _loadChatState.asLiveData()

    private val _chatList = MutableLiveData<MutableList<ChatInfo>>()

    val chatList = _chatList.asLiveData()

    fun loadChat() {
        _loadChatState.value = true
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            when (results) {
                is Results.Successful -> {
                    val list = results.data.toMutableList()
                    _chatList.postValue(list)
                    _loadChatState.value = false
                    _toast.postValue(ToastType.LOAD_CHAT to "初始完成")
                }
                is Results.ClientErrors -> {
                    _loadChatState.value = false
                    _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:無法獲取初始數據")
                }
                is Results.NetWorkError -> {
                    _loadChatState.value = false
                    _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:網路異常")
                }
            }
        }
    }

    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket, chat)

    val message = chatRepo.message

    /**
     * 刷新Chat List(Remove & Insert & Replace)
     * */
    fun refreshChatList(chat: ChatInfo) {
        val list = _chatList.value?.map { it.copy() }?.toMutableList()
        val isRemove = list?.removeAll { it.id == chat.id }
        isRemove?.let { if (it) list.let { _chatList.value = it } }
        sleep(500)
        list?.add(0, chat)
        list?.let { _chatList.value = it }
    }

    private val _scrollToTop = MutableLiveData<Boolean>(false)

    val scrollToTop = _scrollToTop.asLiveData()

    fun scrollToTop(isScrollToTop: Boolean) {
        _scrollToTop.value = isScrollToTop
    }

//    private val _soundState =


    fun switchChatSound(chat: ChatInfo) {
        viewModelScope.launch {
            val results = chatRepo.switchChatSound(chat)
            when (results) {
                is Results.Successful -> {
                    chat.sound = results.data
                    val list = _chatList.value?.map { it.copy() }?.toMutableList()
                    list?.find { it.id == chat.id }?.sound = chat.sound
                    list?.let { _chatList.postValue(it) }
                    _toast.postValue(ToastType.SWITCH_CHAT_NOTIFY to if (results.data) "開啟通知" else "關閉通知")
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.SWITCH_CHAT_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.SWITCH_CHAT_NOTIFY to "${results.e}:網路異常")
                }
            }
        }
    }


}