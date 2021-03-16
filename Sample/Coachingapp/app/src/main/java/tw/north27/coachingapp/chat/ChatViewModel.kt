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
        LOAD_CHAT
    }

    private val _chatList = MutableLiveData<MutableList<ChatInfo>>()

    val chatList = _chatList.asLiveData()

    fun loadChat() {
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            when (results) {
                is Results.Successful -> {
                    val list = results.data.toMutableList()
                    _chatList.postValue(list)
                    _toast.postValue(ToastType.LOAD_CHAT to "初始完成")
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:無法獲取初始數據")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:網路異常")
                }
            }
        }
    }

    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket, chat)

    val message = chatRepo.message

    fun refreshChatList(chat: ChatInfo) {
        val list = _chatList.value?.map { it.copy() }?.toMutableList()
        val isRemove = list?.removeAll { it.id == chat.id }
        isRemove?.let {
            if (it) {
                list.let { _chatList.value = it }
            }
        }
        sleep(500)
        list?.add(0, chat)
        list?.let { _chatList.value = it }
    }

}