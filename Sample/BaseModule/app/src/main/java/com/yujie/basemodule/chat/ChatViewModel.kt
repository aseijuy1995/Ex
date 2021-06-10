//package tw.north27.coachingapp.chat
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
//import com.yujie.utilmodule.base.BaseViewModel
//import com.yujie.utilmodule.http.Results
//import com.yujie.utilmodule.util.ViewState
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import org.koin.core.component.KoinComponent
//import tw.north27.coachingapp.adapter.ChatReadIndex
//import com.yujie.basemodule.ext2.asLiveData
//import tw.north27.coachingapp.model.result.ChatInfo
//
//class ChatViewModel(private val chatRepo: IChatRepository) : BaseViewModel(), KoinComponent {
//
//    private val _toast = MutableLiveData<Pair<ToastType, String>>()
//
//    val toast = _toast.asLiveData()
//
//    /**
//     * 用於判定修改後提示參數
//     * */
//    var type: ChatReadIndex? = null
//
//    enum class ToastType {
//        SWITCH_CHAT_SOUND, DELETE_CHAT_ROOM
//    }
//
//    //
//    /**
//     * 加載聊天列表
//     * */
//    private val chatList = mutableListOf<ChatInfo>()
//
//    private val _chatState = MutableLiveData<ViewState<List<ChatInfo>>>(ViewState.Initial)
//
//    val chatState = _chatState.asLiveData()
//
//    fun loadChat() {
//        chatList.clear()
//        viewModelScope.launch(Dispatchers.IO) {
//            _chatState.postValue(ViewState.load())
//            val results = chatRepo.loadChat()
//            when (results) {
//                is Results.Successful<List<ChatInfo>> -> {
//                    val list = results.data.toMutableList()
//                    if (list.isNullOrEmpty())
//                        _chatState.postValue(ViewState.empty())
//                    else {
//                        chatList.addAll(list)
//                        _chatState.postValue(ViewState.data(chatList))
//                    }
//                }
//                is Results.ClientErrors -> {
//                    _chatState.postValue(ViewState.error(results.e))
//                }
//                is Results.NetWorkError -> {
//                    _chatState.postValue(ViewState.network(results.e))
//                }
//            }
//        }
//
//    }
//
//    val message = chatRepo.message
//
//    /**
//     * 發送socket訊息
//     * */
//    fun send(chat: ChatInfo) = chatRepo.send(chatRepo.webSocket!!, chat)
//
//    /**
//     * 接收socket訊息
//     * */
//    fun receiveChatMessage(chat: ChatInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (chatState.value is ViewState.Data) {
//                chatList.removeAll { it.id == chat.id }
//            }
//            chatList.add(0, chat)
//            _chatState.postValue(ViewState.data(chatList))
//        }
//    }
//
//    /**
//     * 聊天列表置頂
//     * */
//    private val _listScrollToTop = MutableLiveData<Boolean>(false)
//
//    val listScrollToTop = _listScrollToTop.asLiveData()
//
//    fun listScrollToTop(isScrollToTop: Boolean) {
//        _listScrollToTop.value = isScrollToTop
//    }
//
//    private val _chatSoundState = MutableLiveData<ViewState<ChatInfo>>(ViewState.Initial)
//
//    val chatSoundState = _chatSoundState.asLiveData()
//
//    /**
//     * 聲音刷新
//     * result.data
//     * true - change success
//     * failed - change failed
//     * */
//    fun switchChatSound(type: ChatReadIndex, chat: ChatInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _chatSoundState.postValue(ViewState.load())
//            this@ChatViewModel.type = type
//            chat.isSound = !chat.isSound
//            val results = chatRepo.switchChatSound(chat)
//            when (results) {
//                is Results.Successful<List<ChatInfo>> -> {
//                    when (results.data) {
//                        true -> {
//                            if (chatState.value is ViewState.Data) {
//                                chatList.find { it.id == chat.id }?.isSound = chat.isSound
//                                _chatSoundState.postValue(ViewState.data(chat))
//                                _chatState.postValue(ViewState.data(chatList))
//                            }
//                        }
//                        false -> {
//                        }
//                    }
//                }
//                is Results.ClientErrors -> {
//                    _chatSoundState.postValue(ViewState.error(results.e))
//                }
//                is Results.NetWorkError -> {
//                    _chatSoundState.postValue(ViewState.network(results.e))
//                }
//            }
//        }
//    }
//
//    private val _chatDeleteState = MutableLiveData<ViewState<ChatInfo>>(ViewState.Initial)
//
//    val chatDeleteState = _chatSoundState.asLiveData()
//
//    /**
//     * 刪除聊天室
//     * result.data
//     * true - change success
//     * failed - change failed
//     * */
//    fun deleteChatRoom(type: ChatReadIndex, chat: ChatInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _chatDeleteState.postValue(ViewState.load())
//            this@ChatViewModel.type = type
//            val results = chatRepo.deleteChatRoom(chat)
//            when (results) {
//                is Results.Successful<List<ChatInfo>> -> {
//                    when (results.data) {
//                        true -> {
//                            if (chatState.value is ViewState.Data) {
//                                val isRemove = chatList.removeAll { it.id == chat.id }
//                                isRemove.let {
//                                    if (it) {
//                                        if (chatList.isEmpty()) {
//                                            _chatState.postValue(ViewState.empty())
//                                        } else {
//                                            _chatDeleteState.postValue(ViewState.data(chat))
//                                            _chatState.postValue(ViewState.data(chatList))
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        false -> {
//                        }
//                    }
//                }
//                is Results.ClientErrors -> {
//                    _chatDeleteState.postValue(ViewState.error(results.e))
//                }
//                is Results.NetWorkError -> {
//                    _chatDeleteState.postValue(ViewState.network(results.e))
//                }
//            }
//        }
//
//    }
//
//
//}