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
import tw.north27.coachingapp.model.request.AskRoomRequest
import tw.north27.coachingapp.model.request.PushRequest
import tw.north27.coachingapp.model.request.SoundRequest
import tw.north27.coachingapp.model.response.PushResponse
import tw.north27.coachingapp.model.response.SoundResponse
import tw.north27.coachingapp.repository.IActionRepository

class AskViewModel(
    application: Application,
    private val actionRepo: IActionRepository
) : BaseAndroidViewModel(application) {

    private val _askRoomListState = MutableLiveData<ViewState<List<AskRoom>>>(ViewState.Initial)

    val askRoomListState = _askRoomListState.asLiveData()

    fun fetchAskRoomList(id: Long? = null) = viewModelScope.launch(Dispatchers.IO) {
        _askRoomListState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = actionRepo.fetchAskRoomList(
            AskRoomRequest(
                account = account,
                topAskId = id
            )
        )
        when (results) {
            is Results.Successful<List<AskRoom>> -> {
                val list = results.data
                if (list.isNullOrEmpty())
                    _askRoomListState.postValue(ViewState.empty())
                else {
                    _askRoomListState.postValue(ViewState.data(list))
                }
            }
            is Results.ClientErrors -> {
                _askRoomListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askRoomListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _pushState = MutableLiveData<ViewState<PushResponse>>(ViewState.Initial)

    val pushState = _pushState.asLiveData()

    fun updateAskRoomPush(id: Long, state: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _pushState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = actionRepo.updateAskRoomPush(
            PushRequest(
                roomId = id,
                account = account,
                isState = state
            )
        )
        when (results) {
            is Results.Successful<PushResponse> -> {
                val pushResponse = results.data
                _pushState.postValue(ViewState.data(pushResponse))
            }
            is Results.ClientErrors -> {
                _pushState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _pushState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _soundState = MutableLiveData<ViewState<SoundResponse>>(ViewState.Initial)

    val soundState = _soundState.asLiveData()

    fun updateAskRoomSound(id: Long, state: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _soundState.postValue(ViewState.load())
        val account = cxt.userPref.getAccount().first()
        val results = actionRepo.updateAskRoomSound(
            SoundRequest(
                roomId = id,
                account = account,
                isState = state
            )
        )
        when (results) {
            is Results.Successful<SoundResponse> -> {
                val soundResponse = results.data
                _soundState.postValue(ViewState.data(soundResponse))
            }
            is Results.ClientErrors -> {
                _soundState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _soundState.postValue(ViewState.network(results.e))
            }
        }
    }


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