package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.util.ViewState

class ChatViewModel(private val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    /**
     * 用於判定修改後提示參數
     * */
    var type: ChatReadIndex? = null

    enum class ToastType {
        SWITCH_CHAT_SOUND, DELETE_CHAT_ROOM
    }

    //
    /**
     * 加載聊天列表
     * */
    private val chatList = mutableListOf<ChatInfo>()

    private val _chatState = MutableLiveData<ViewState<List<ChatInfo>>>(ViewState.Initial)

    val chatState = _chatState.asLiveData()

    fun loadChat() {
        chatList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.postValue(ViewState.load())
            val results = chatRepo.loadChat()
            when (results) {
                is Results.Successful -> {
                    val list = results.data.toMutableList()
                    if (list.isNullOrEmpty())
                        _chatState.postValue(ViewState.empty())
                    else {
                        chatList.addAll(list)
                        _chatState.postValue(ViewState.data(chatList))
                    }
                }
                is Results.ClientErrors -> {
                    _chatState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _chatState.postValue(ViewState.network(results.e))
                }
            }
        }

    }

    val message = chatRepo.message

    /**
     * 發送socket訊息
     * */
    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket!!, chat)

    /**
     * 接收socket訊息
     * */
    fun receiveChatMessage(chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            if (chatState.value is ViewState.Data) {
                chatList.removeAll { it.id == chat.id }
            }
            chatList.add(0, chat)
            _chatState.postValue(ViewState.data(chatList))
        }
    }

    /**
     * 聊天列表置頂
     * */
    private val _listScrollToTop = MutableLiveData<Boolean>(false)

    val listScrollToTop = _listScrollToTop.asLiveData()

    fun listScrollToTop(isScrollToTop: Boolean) {
        _listScrollToTop.value = isScrollToTop
    }

    private val _chatSoundState = MutableLiveData<ViewState<ChatInfo>>(ViewState.Initial)

    val chatSoundState = _chatSoundState.asLiveData()

    /**
     * 聲音刷新
     * result.data
     * true - change success
     * failed - change failed
     * */
    fun switchChatSound(type: ChatReadIndex, chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatSoundState.postValue(ViewState.load())
            this@ChatViewModel.type = type
            chat.isSound = !chat.isSound
            val results = chatRepo.switchChatSound(chat)
            when (results) {
                is Results.Successful -> {
                    when (results.data) {
                        true -> {
                            if (chatState.value is ViewState.Data) {
                                chatList.find { it.id == chat.id }?.isSound = chat.isSound
                                _chatSoundState.postValue(ViewState.data(chat))
                                _chatState.postValue(ViewState.data(chatList))
                            }
                        }
                        false -> {
                            _chatSoundState.postValue(ViewState.empty())
                        }
                    }
                }
                is Results.ClientErrors -> {
                    _chatSoundState.postValue(ViewState.error(results.e))
                }
                is Results.NetWorkError -> {
                    _chatSoundState.postValue(ViewState.network(results.e))
                }
            }
        }
    }

    /**
     * 刪除聊天室
     * result.data
     * true - change success
     * failed - change failed
     * */
    fun deleteChatRoom(type: ChatReadIndex, chat: ChatInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            this@ChatViewModel.type = type
            val results = chatRepo.deleteChatRoom(chat)
            when (results) {
                is Results.Successful -> {
                    when (results.data) {
                        true -> {
                            if (chatState.value is ViewState.Data) {
                                val list = (chatState.value as ViewState.Data).data.map { it.copy() }.toMutableList()
                                val isRemove = list.removeAll { it.id == chat.id }
                                isRemove.let {
                                    if (it) {
                                        if (list.isEmpty())
                                            _chatState.postValue(ViewState.empty())
                                        else
                                            _chatState.postValue(ViewState.data(list))
                                    }
                                }
                                _toast.postValue(ToastType.DELETE_CHAT_ROOM to "已刪除!")
                            }
                        }
                        false -> {
                            _toast.postValue(ToastType.DELETE_CHAT_ROOM to "${results.data}:修改錯誤，請稍後再刪除!")
                        }
                    }
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.DELETE_CHAT_ROOM to "${results.e}:修改錯誤，請稍後再修改!")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.DELETE_CHAT_ROOM to "${results.e}:網路異常")
                }
            }
        }

    }


}