package tw.north27.coachingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyViewModel(private val notifyRepo: INotifyRepository) : BaseViewModel() {


    fun notifyList(): LiveData<PagingData<NotifyInfo>> = notifyRepo.loadNotify().flow.cachedIn(viewModelScope).asLiveData()

    private val _notifyOpen = MutableLiveData<Boolean>()

    val notifyOpen = _notifyOpen.asLiveData()

    private val _toast = MutableLiveData<Pair<ToastType, String>>()

    val toast = _toast.asLiveData()

    enum class ToastType {
        OPEN_NOTIFY, READ_ALL_NOTIFY
    }

    fun switchNotify(isOpen: Boolean) {
        viewModelScope.launch {
            val results = notifyRepo.switchNotify(isOpen)
            when (results) {
                is Results.Successful -> {
                    _notifyOpen.postValue(results.data!!)
                    _toast.postValue(ToastType.OPEN_NOTIFY to if (results.data) "開啟通知" else "關閉通知")
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.OPEN_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.OPEN_NOTIFY to "${results.e}:網路異常")
                }
            }
        }
    }

    fun readAllNotify() {
        viewModelScope.launch {
            val results = notifyRepo.readAllNotify()
            when (results) {
                is Results.Successful -> {
                    _notifyOpen.postValue(results.data!!)
                    _toast.postValue(ToastType.READ_ALL_NOTIFY to if (results.data) "全部已讀" else "已全部已讀摟!")
                }
                is Results.ClientErrors -> {
                    _toast.postValue(ToastType.READ_ALL_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
                }
                is Results.NetWorkError -> {
                    _toast.postValue(ToastType.READ_ALL_NOTIFY to "${results.e}:網路異常")
                }
            }
        }
    }


    private val _notifyInfoMore = MutableLiveData<NotifyInfo?>()

    val notifyInfoMore = _notifyInfoMore.asLiveData()

    fun setNotifyInfoToMorePage(notifyInfo: NotifyInfo?) {
        _notifyInfoMore.postValue(notifyInfo!!)
    }

//    private val _notifyLists = mutableListOf<NotifyInfo>()
//
//    fun deleteNotifyInfo(notifyInfo: NotifyInfo) {
//        val isRemove = _notifyLists.remove(notifyInfo)
//        if (isRemove)
//            _notifyListLiveData.postValue(_notifyLists)
//
//    }
}