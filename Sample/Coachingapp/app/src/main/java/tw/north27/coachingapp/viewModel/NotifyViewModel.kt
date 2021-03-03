package tw.north27.coachingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import tw.north27.coachingapp.base.viewModel.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyViewModel(private val notifyRepo: INotifyRepository) : BaseViewModel() {
//
//    private val _notifyListLiveData by MutableLiveData<PagingData<NotifyInfo>>{
//
//    }

    fun notifyInfoList(): LiveData<PagingData<NotifyInfo>> {
        return notifyRepo.postLoadNotifyPager(1).flow.cachedIn(viewModelScope).asLiveData()
    }
    
    private val _notifyInfoMore = MutableLiveData<NotifyInfo?>()

    val notifyInfoMore = _notifyInfoMore.asLiveData()

    fun setNotifyInfoToMorePage(notifyInfo: NotifyInfo?) {
        _notifyInfoMore.postValue(notifyInfo)
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