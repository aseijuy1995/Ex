package tw.north27.coachingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tw.north27.coachingapp.base.viewModel.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyViewModel(private val notifyRepo: INotifyRepository) : BaseViewModel() {

    private val _notifyList = MutableLiveData<List<NotifyInfo>>()

    private val _notifys = mutableListOf<NotifyInfo>()

    fun notifyList(): LiveData<List<NotifyInfo>> {
        viewModelScope.launch {
            val notifyList = notifyRepo.postNotifyList(0, 10)
            _notifys.addAll(notifyList)
            _notifyList.postValue(_notifys)
        }
        return _notifyList.asLiveData()
    }

    private val _notifyInfoMore = MutableLiveData<NotifyInfo?>()

    val notifyInfoMore = _notifyInfoMore.asLiveData()

    fun setNotifyInfoToMorePage(notifyInfo: NotifyInfo?) {
        _notifyInfoMore.postValue(notifyInfo)
    }

    fun deleteNotifyInfo(notifyInfo: NotifyInfo) {
        val isRemove = _notifys.remove(notifyInfo)
        if (isRemove)
            _notifyList.postValue(_notifys)

    }
}