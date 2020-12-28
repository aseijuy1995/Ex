package edu.yujie.lohasapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.yujie.lohasapp.bean.AppResult
import edu.yujie.lohasapp.repo.OriginateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OriginateViewModel(private val repo: OriginateRepository) : ViewModel() {

    val mVersion: MutableLiveData<AppResult> by lazy { MutableLiveData<AppResult>() }

    fun postVersion(): LiveData<AppResult> {
        viewModelScope.launch(Dispatchers.IO) {
            val appResult = repo.postVersion()
            mVersion.postValue(appResult)
        }
        return mVersion
    }
}