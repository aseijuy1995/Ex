package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.ext.asStateFlow
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.util.ViewState
import java.lang.Thread.sleep

class ChatViewModel(private val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    /**
     * 用於判定修改後提示參數
     * */
    var type: ChatReadIndex? = null

    enum class ToastType {
        LOAD_CHAT, SWITCH_CHAT_SOUND, DELETE_CHAT_ROOM
    }

    /**
     * 加載聊天列表
     * */
    private val _loadChatState = MutableStateFlow<ViewState<List<ChatInfo>>>(ViewState.Load)

    val loadChatState = _loadChatState.asStateFlow()

    fun loadChat() = viewModelScope.launch(Dispatchers.IO) {
        _loadChatState.value = ViewState.load()
        val results = chatRepo.loadChat()
        when (results) {
            is Results.Successful -> {
                val list = results.data.toMutableList()
                _loadChatState.value = if (list.isNullOrEmpty()) ViewState.empty()
                else ViewState.data(list)
                _toast.postValue(ToastType.LOAD_CHAT to "初始完成")
            }
            is Results.ClientErrors -> {
                _loadChatState.value = ViewState.error(results.e)
                _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:無法獲取初始數據")
            }
            is Results.NetWorkError -> {
                _loadChatState.value = ViewState.network(results.e)
                _toast.postValue(ToastType.LOAD_CHAT to "${results.e}:網路異常")
            }
        }
    }

    val message = chatRepo.message

    /**
     * 接收socket訊息
     * */
    fun receiveChatMessage(chat: ChatInfo) {
        var list = mutableListOf<ChatInfo>()
        if (loadChatState.value is ViewState.Data) {
            list = (loadChatState.value as ViewState.Data).data.toMutableList()
            val isRemove = list.removeAll { it.id == chat.id }
            isRemove.let { if (it) _loadChatState.value = ViewState.data(list) }
            sleep(500)
        }
        val list2 = list.map { it.copy() }.toMutableList()
        list2.add(0, chat)
        _loadChatState.value = ViewState.data(list2)
    }

    /**
     * 聲音刷新
     * result.data
     * true - change success
     * failed - change failed
     * */
    fun switchChatSound(type: ChatReadIndex, chat: ChatInfo) {
        viewModelScope.launch {
            this@ChatViewModel.type = type
            chat.isSound = !chat.isSound
            val results = chatRepo.switchChatSound(chat)
            when (results) {
                is Results.Successful -> {
                    when (results.data) {
                        true -> {
                            if (loadChatState.value is ViewState.Data) {
                                val list = (loadChatState.value as ViewState.Data).data.map { it.copy() }.toMutableList()
                                list.find { it.id == chat.id }?.isSound = !chat.isSound
                                _loadChatState.value = ViewState.data(list)
                            }

                            _toast.postValue(ToastType.SWITCH_CHAT_SOUND to if (!chat.isSound) "開啟通知" else "關閉通知")
                        }
                        false -> {
                            _toast.postValue(ToastType.SWITCH_CHAT_SOUND to "${results.data}:修改錯誤，請稍後再修改!")
                        }
                    }
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.SWITCH_CHAT_SOUND to "${results.e}:修改錯誤，請稍後再修改!")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.SWITCH_CHAT_SOUND to "${results.e}:網路異常")
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
    fun deleteChatRoom(type: ChatReadIndex, chat: ChatInfo) = viewModelScope.launch(Dispatchers.IO) {
        this@ChatViewModel.type = type
        val results = chatRepo.deleteChatRoom(chat)
        when (results) {
            is Results.Successful -> {
                when (results.data) {
                    true -> {
                        if (loadChatState.value is ViewState.Data) {
                            val list = (loadChatState.value as ViewState.Data).data.map { it.copy() }.toMutableList()
                            val isRemove = list.removeAll { it.id == chat.id }
                            isRemove.let {
                                if (it) {
                                    _loadChatState.value = if (list.isEmpty()) ViewState.empty()
                                    else ViewState.data(list)
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

    /**
     * 發送socket訊息
     * */
    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket!!, chat)

    /**
     * 聊天列表置頂
     * */
    private val _listScrollToTop = MutableLiveData<Boolean>(false)

    val listScrollToTop = _listScrollToTop.asLiveData()

    fun listScrollToTop(isScrollToTop: Boolean) {
        _listScrollToTop.value = isScrollToTop
    }

}