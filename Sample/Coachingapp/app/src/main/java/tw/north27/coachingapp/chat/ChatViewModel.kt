package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import tw.north27.coachingapp.R
import tw.north27.coachingapp.adapter.ChatReadIndex
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results
import java.lang.Thread.sleep

class ChatViewModel(val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    /**
     * 用於判定修改後提示參數
     * */
    var type: ChatReadIndex? = null

    enum class ToastType {
        LOAD_CHAT, SWITCH_CHAT_SOUND, DELETE_CHAT_ROOM, LOAD_CHAT_MESSAGE_LIST
    }

    /**
     * 加載聊天列表
     * */
    private val _chatListState = MutableLiveData<Boolean>(true)

    val chatListState = _chatListState.asLiveData()

    private val _chatList = MutableLiveData<MutableList<ChatInfo>?>()

    val chatList = _chatList.asLiveData()

    fun loadChat() {
        _chatListState.value = true
        _chatList.value = null
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            _chatListState.value = false
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

    /**
     * 聊天列表置頂
     * */
    private val _listScrollToTop = MutableLiveData<Boolean>(false)

    val listScrollToTop = _listScrollToTop.asLiveData()

    fun listScrollToTop(isScrollToTop: Boolean) {
        _listScrollToTop.value = isScrollToTop
    }

//    /**
//     * fab顯示與否
//     * */
//    private val _showFab = MutableLiveData<Boolean>(false)
//
//    val showFab = _showFab.asLiveData()
//
//    fun showFab(isShow: Boolean) {
//        _showFab.value = isShow
//    }

    /**
     *
     * */
    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket, chat)

    val message = chatRepo.message

    //--------------------------------------------------------------------------------------------------------
    //ChatListFragment
    /**
     * 刷新Chat List(Remove & Insert & Replace)
     * */
    fun refreshChatList(chat: ChatInfo) {
        val listRemove = _chatList.value?.map { it.copy() }?.toMutableList()
        val isRemove = listRemove?.removeAll { it.id == chat.id }
        isRemove?.let { if (it) listRemove.let { _chatList.value = it } }
        sleep(500)
        val listAdd = listRemove?.map { it.copy() }?.toMutableList()
        listAdd?.add(0, chat)
        listAdd?.let { _chatList.value = it }
    }

    /**
     * 聲音刷新
     * result.data
     * true - change success
     * failed - change failed
     * */
    fun switchChatSound(type: ChatReadIndex, chat: ChatInfo) {
        this.type = type
        val chatChanging = chat.copy(isSound = !chat.isSound)
        viewModelScope.launch {
            val results = chatRepo.switchChatSound(chatChanging)
            when (results) {
                is Results.Successful -> {
                    when (results.data) {
                        true -> {
                            val listUpdate = _chatList.value?.map { it.copy() }?.toMutableList()
                            listUpdate?.find { it.id == chat.id }?.isSound = !chat.isSound
                            _chatList.postValue(listUpdate)
                            //負負得正
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
    fun deleteChatRoom(type: ChatReadIndex, chat: ChatInfo) {
        this.type = type
        viewModelScope.launch {
            val results = chatRepo.deleteChatRoom(chat)
            when (results) {
                is Results.Successful -> {
                    when (results.data) {
                        true -> {
                            val listUpdate = _chatList.value?.map { it.copy() }?.toMutableList()
                            val isRemove = listUpdate?.removeAll { it.id == chat.id }
                            isRemove?.let { if (it) listUpdate.let { _chatList.postValue(it) } }
                            _toast.postValue(ToastType.DELETE_CHAT_ROOM to "已刪除!")
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


    //--------------------------------------------------------------------------------------------------------

    private val _inputEmpty = MutableLiveData<Boolean>(true)

    val inputEmpty = _inputEmpty.asLiveData()

    val inputRes = _inputEmpty.map {
        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    fun inputEmpty(isInputEmpty: Boolean) {
        _inputEmpty.value = isInputEmpty
    }

    private val _chatMessageList = MutableLiveData<List<ChatInfo>>()

    val chatMessageList = _chatMessageList.asLiveData()

    fun chatMessageList() {
        viewModelScope.launch {
            val results = chatRepo.loadChatList()
            when (results) {
                is Results.Successful -> {
                    _chatMessageList.postValue(results.data!!)
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

    /**
     * 聊天室列表置底
     * */

    private val _roomScrollToBottom = MutableLiveData<Boolean>(false)

    val roomScrollToBottom = _roomScrollToBottom.asLiveData()

    fun roomScrollToBottom(isScrollToBottom: Boolean) {
        _roomScrollToBottom.value = isScrollToBottom
    }
}