package com.example.websockerext.pattern

open class SingletonParamsHolder<T : Any, A>(private val creator: (A) -> T) {
    private var sInstance: T? = null

    fun getInstance(arg: A): T = sInstance ?: synchronized(this) {
        sInstance ?: creator(arg).also {
            sInstance = it
        }
    }
}