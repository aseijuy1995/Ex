package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatRead
import tw.north27.coachingapp.module.http.Results

class ChatListViewModel(val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        LOAD_CHAT
    }

    private val _chatList = MutableLiveData<MutableList<ChatInfo>>(mutableListOf())

    val chatList = _chatList.asLiveData()

    fun loadChat(type: ChatReadIndex) {
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            val list: List<ChatInfo>
            when (results) {
                is Results.Successful -> {
                    when (type) {
                        ChatReadIndex.ALL -> {
                            list = results.data
                        }
                        ChatReadIndex.HAVE_READ -> {
                            list = results.data.filter { it.read == ChatRead.HAVE_READ }
                        }
                        ChatReadIndex.UN_READ -> {
                            list = results.data.filter { it.read == ChatRead.UN_READ }
                        }
                    }
                    _chatList.postValue(list.toMutableList())
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


//    val serverInfoRelay = chatModule.serverInfoRelay
//
//    val infoRelay = chatModule.infoRelay

    private val chatModule by inject<IChatModule>()

    fun send(chat: ChatInfo) = chatModule.send(chatModule.webSocket, chat)

    val messageRelay = chatModule.messageRelay

    fun refreshChatList(chat: ChatInfo) {
        _chatList.value?.removeAll { it.id == chat.id }
        val list = _chatList.value
        list?.add(0, chat)
        _chatList.postValue(list!!)
    }


}