package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.AskRoom
import tw.north27.coachingapp.model.request.AskRoomRequest
import tw.north27.coachingapp.model.request.PushRequest
import tw.north27.coachingapp.model.request.SoundRequest
import tw.north27.coachingapp.model.response.PushResponse
import tw.north27.coachingapp.model.response.SoundResponse
import tw.north27.coachingapp.repository.IAskRoomRepository

class AskViewModel(
    application: Application,
    private val askRoomRepo: IAskRoomRepository
) : BaseAndroidViewModel(application) {

    private val _askRoomListState = MutableLiveData<ViewState<List<AskRoom>>>(ViewState.Initial)

    val askRoomListState = _askRoomListState.asLiveData()

    val lastAskRoomListState = MutableLiveData<ViewState<List<AskRoom>>>(ViewState.Initial)

    //啟用刷新視圖
    private val _isRefreshView = MutableLiveData<Boolean>(true)

    val isRefreshView = _isRefreshView.asLiveData()

    fun setRefreshView(isRefreshView: Boolean) {
        _isRefreshView.postValue(isRefreshView)
    }

    fun fetchAskRoomList(askId: Long) = viewModelScope.launch(Dispatchers.IO) {
        _askRoomListState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = askRoomRepo.fetchAskRoomList(
            AskRoomRequest(
                clientId = clientId,
                askId = if (askId == -1L) null else askId
            )
        )
        when (results) {
            is Results.Successful<List<AskRoom>> -> {
                val list = results.data
                if (list.isNullOrEmpty()) {
                    _askRoomListState.postValue(ViewState.empty())
                    lastAskRoomListState.postValue(ViewState.empty())
                } else {
                    _askRoomListState.postValue(ViewState.data(list))
                    lastAskRoomListState.postValue(ViewState.data(list))
                }
            }
            is Results.ClientErrors -> {
                _askRoomListState.postValue(ViewState.error(results.e))
                lastAskRoomListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askRoomListState.postValue(ViewState.network(results.e))
                lastAskRoomListState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _pushState = MutableLiveData<ViewState<PushResponse>>(ViewState.Initial)

    val pushState = _pushState.asLiveData()

    //roomId to state
    private var pushPair: Pair<Long, Boolean>? = null

    fun updateAskRoomPush(roomId: Long, state: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _pushState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        pushPair = (roomId to state)
        val results = askRoomRepo.updateAskRoomPush(
            PushRequest(
                roomId = roomId,
                clientId = clientId,
                isState = state
            )
        )
        when (results) {
            is Results.Successful<PushResponse> -> {
                val pushResponse = results.data.apply {
                    this.roomId = pushPair?.first
                    this.isState = pushPair?.second
                }
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

    //roomId to state
    private var soundPair: Pair<Long, Boolean>? = null

    fun updateAskRoomSound(roomId: Long, state: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _soundState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        soundPair = (roomId to state)
        val results = askRoomRepo.updateAskRoomSound(
            SoundRequest(
                roomId = roomId,
                clientId = clientId,
                isState = state
            )
        )
        when (results) {
            is Results.Successful<SoundResponse> -> {
                val soundResponse = results.data.apply {
                    this.roomId = soundPair?.first
                    this.isState = soundPair?.second
                }
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


}