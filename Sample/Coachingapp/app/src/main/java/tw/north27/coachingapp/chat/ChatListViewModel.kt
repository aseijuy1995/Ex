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

    private val _chatAllList = MutableLiveData<MutableList<ChatInfo>>(mutableListOf())
    private val _chatHaveReadList = MutableLiveData<MutableList<ChatInfo>>(mutableListOf())
    private val _chatUnReadList = MutableLiveData<MutableList<ChatInfo>>(mutableListOf())

    val chatAllList = _chatAllList.asLiveData()
    val chatHaveReadList = _chatHaveReadList.asLiveData()
    val chatUnReadList = _chatUnReadList.asLiveData()

    fun loadChat(type: ChatReadIndex) {
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            val list: List<ChatInfo>
            when (results) {
                is Results.Successful -> {
                    when (type) {
                        ChatReadIndex.ALL -> {
                            list = results.data
                            _chatAllList.postValue(list.toMutableList())
                        }
                        ChatReadIndex.HAVE_READ -> {
                            list = results.data.filter { it.read == ChatRead.HAVE_READ }
                            _chatHaveReadList.postValue(list.toMutableList())
                        }
                        ChatReadIndex.UN_READ -> {
                            list = results.data.filter { it.read == ChatRead.UN_READ }
                            _chatUnReadList.postValue(list.toMutableList())
                        }
                    }

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

    fun refreshChatList(type: ChatReadIndex, chat: ChatInfo) {
        val list = when (type) {
            ChatReadIndex.ALL -> {
                _chatAllList.value?.removeAll { it.id == chat.id }
                _chatAllList.value
            }
            ChatReadIndex.HAVE_READ -> {
                _chatHaveReadList.value?.removeAll { it.id == chat.id }
                _chatHaveReadList.value
            }
            ChatReadIndex.UN_READ -> {
                _chatUnReadList.value?.removeAll { it.id == chat.id }
                _chatUnReadList.value
            }
        }
        list?.add(0, chat)
        when (type) {
            ChatReadIndex.ALL -> {
                _chatAllList.postValue(list!!)
            }
            ChatReadIndex.HAVE_READ -> {
                _chatHaveReadList.postValue(list!!)
            }
            ChatReadIndex.UN_READ -> {
                _chatUnReadList.postValue(list!!)
            }
        }
    }


}