package edu.yujie.socketex.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.yujie.socketex.bean.InitSettingBean
import edu.yujie.socketex.inter.IApiRepo
import edu.yujie.socketex.util.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartVM(private val repo: IApiRepo) : ViewModel() {

    private val _initData = MutableLiveData<InitSettingBean>()

    fun getInitData(): LiveData<InitSettingBean> {
        viewModelScope.launch(Dispatchers.Main) {
            _initData.value = repo.getInit()
        }
        return _initData.asLiveData()
    }

}