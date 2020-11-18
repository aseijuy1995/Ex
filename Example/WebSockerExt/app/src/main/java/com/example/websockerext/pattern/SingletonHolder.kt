package com.example.websockerext.pattern

open class SingletonHolder<T : Any>(private val creator: () -> T) {
    private var sInstance: T? = null

    fun getInstance(): T = sInstance ?: synchronized(this) {
        sInstance ?: creator().also {
            sInstance = it
        }
    }
}