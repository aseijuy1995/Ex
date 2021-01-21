package edu.yujie.socketex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

public inline fun <T> mutableLiveData(): MutableLiveData<T> {
    val mutableLiveData: MutableLiveData<T> by lazy { MutableLiveData<T>() }
    return mutableLiveData
}