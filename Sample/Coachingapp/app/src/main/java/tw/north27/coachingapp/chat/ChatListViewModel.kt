package tw.north27.coachingapp.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.module.http.Results

class ChatListViewModel(val chatRepo: IChatRepository) : BaseViewModel() {

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        LOAD_CHAT
    }

    private val _chatList = MutableLiveData<List<ChatInfo>>()

    val chatList = _chatList.asLiveData()

    fun loadChat(): LiveData<List<ChatInfo>> {
        viewModelScope.launch {
            val results = chatRepo.loadChat()
            when (results) {
                is Results.Successful -> {
                    _chatList.postValue(results.data!!)
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
        return chatList
    }
}