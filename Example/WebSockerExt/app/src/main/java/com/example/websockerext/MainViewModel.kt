package com.example.websockerext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {
    private val mAppResult = MutableLiveData<AppResult>()
    val appResult: LiveData<AppResult> = mAppResult


    fun postAppResult(): LiveData<AppResult> {
        viewModelScope.launch(Dispatchers.IO) {
            mAppResult.postValue(repo.postAppResult())
        }
        return appResult
    }
}