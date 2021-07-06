package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseAndroidViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.request.AskRequest
import tw.north27.coachingapp.repository.IActionRepository

class AskViewModel(
    application: Application,
    private val actionRepo: IActionRepository
) : BaseAndroidViewModel(application) {

    private val _askInfoListState = MutableLiveData<ViewState<List<AskRoom>>>(ViewState.Initial)

    val askInfoListState = _askInfoListState.asLiveData()

    fun fetchAskRoomList(id: Long? = null) = viewModelScope.launch(Dispatchers.IO) {
        _askInfoListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = actionRepo.fetchAskRoomList(
            AskRequest(
                account = account,
                topAskId = id
            )
        )
        when (results) {
            is Results.Successful<List<AskRoom>> -> {
                val list = results.data
                if (list.isNullOrEmpty())
                    _askInfoListState.postValue(ViewState.empty())
                else {
                    _askInfoListState.postValue(ViewState.data(list))
                }
            }
            is Results.ClientErrors -> {
                _askInfoListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askInfoListState.postValue(ViewState.network(results.e))
            }
        }
    }


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
//            this@AskViewModel.type = type
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
//            this@AskViewModel.type = type
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


}