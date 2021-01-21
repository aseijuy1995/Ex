package edu.yujie.socketex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

public inline fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

public inline fun <T> mutableLiveData(): MutableLiveData<T> {
    val mutableLiveData: MutableLiveData<T> by lazy { MutableLiveData<T>() }
    return mutableLiveData
}

//public inline fun <T> MutableStateFlow<T>.asStateFlow() = this as StateFlow<T>

public inline fun <T> mutableStateFlow(value: T): MutableStateFlow<T> {
    val mutableStateFlow: MutableStateFlow<T> by lazy { MutableStateFlow<T>(value) }
    return mutableStateFlow
}