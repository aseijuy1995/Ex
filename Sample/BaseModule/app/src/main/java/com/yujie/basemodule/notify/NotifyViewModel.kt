//package tw.north27.coachingapp.notify
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.asLiveData
//import androidx.paging.PagingData
//import androidx.paging.cachedIn
//import tw.north27.coachingapp.ext2.asLiveData
//import tw.north27.coachingapp.model.result.NotifyInfo
//
//class NotifyViewModel(private val notifyRepo: INotifyRepository) : BaseViewModel() {
//
//    fun getNotifyList(): LiveData<PagingData<NotifyInfo>> = notifyRepo.loadNotify().flow.cachedIn(viewModelScope).asLiveData()
//
//    private val _notifyOpen = MutableLiveData<Boolean>()
//
//    val notifyOpen = _notifyOpen.asLiveData()
//
//    private val _toast = MutableLiveData<Pair<ToastType, String>>()
//
//    val toast = _toast.asLiveData()
//
//    enum class ToastType {
//        OPEN_NOTIFY, READ_ALL_NOTIFY, DELETE_NOTIFY
//    }
//
//    fun switchNotify(isOpen: Boolean) {
//        viewModelScope.launch {
//            val results = notifyRepo.switchNotify(isOpen)
//            when (results) {
//                is Results.Successful -> {
//                    _notifyOpen.postValue(results.data!!)
//                    _toast.postValue(ToastType.OPEN_NOTIFY to if (results.data) "開啟通知" else "關閉通知")
//                }
//                is Results.ClientErrors -> {
//                    _toast.postValue(ToastType.OPEN_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
//                }
//                is Results.NetWorkError -> {
//                    _toast.postValue(ToastType.OPEN_NOTIFY to "${results.e}:網路異常")
//                }
//            }
//        }
//    }
//
//    fun readAllNotify() {
//        viewModelScope.launch {
//            val results = notifyRepo.readAllNotify()
//            when (results) {
//                is Results.Successful -> {
////                    _notifyOpen.postValue(results.data!!)
//                    _toast.postValue(ToastType.READ_ALL_NOTIFY to if (results.data) "全部已讀" else "已全部已讀摟!")
//                }
//                is Results.ClientErrors -> {
//                    _toast.postValue(ToastType.READ_ALL_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
//                }
//                is Results.NetWorkError -> {
//                    _toast.postValue(ToastType.READ_ALL_NOTIFY to "${results.e}:網路異常")
//                }
//            }
//        }
//    }
//
//    private val _notifyDelete = MutableLiveData<NotifyInfo>()
//
//    val notifyDelete = _notifyDelete.asLiveData()
//
//    fun deleteNotify(notifyInfo: NotifyInfo) {
//        viewModelScope.launch {
//            val results = notifyRepo.deleteNotify(notifyInfo)
//            when (results) {
//                is Results.Successful -> {
//                    when (results.data) {
//                        true -> {
//                            _notifyDelete.postValue(notifyInfo)
//                            _toast.postValue(ToastType.DELETE_NOTIFY to "已移除通知")
//                        }
//                        false -> {
//                            _toast.postValue(ToastType.DELETE_NOTIFY to "${results.data}:修改錯誤，請稍後再刪除!")
//                        }
//                    }
//                }
//                is Results.ClientErrors -> {
//                    _toast.postValue(ToastType.DELETE_NOTIFY to "${results.e}:修改錯誤，請稍後再修改!")
//                }
//                is Results.NetWorkError -> {
//                    _toast.postValue(ToastType.DELETE_NOTIFY to "${results.e}:網路異常")
//                }
//            }
//        }
//    }
//}