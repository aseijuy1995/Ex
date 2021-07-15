package com.yujie.core_lib.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun <T> mutableLiveData(value: T): MutableLiveData<T> {
		val mutableLiveData: MutableLiveData<T> by lazy { MutableLiveData<T>(value) }
		return mutableLiveData
}

fun <T> MutableStateFlow<T>.asStateFlow() = this as StateFlow<T>

fun <T> mutableStateFlow(value: T): MutableStateFlow<T> {
		val mutableStateFlow: MutableStateFlow<T> by lazy { MutableStateFlow<T>(value) }
		return mutableStateFlow
}