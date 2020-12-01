package edu.yujie.coroutinesex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MyViewModel : ViewModel() {

    val liveData = MutableLiveData<String>()

    fun loadData(): LiveData<String> {
        viewModelScope.launch(Dispatchers.IO) {
            val value = loadDataFromViewModel()
            liveData.postValue(value)
        }
        return liveData
    }
}