package edu.yujie.livedataex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */

class PairLiveData<A, B>(first: LiveData<A>, second: LiveData<B>) :
    MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(first) { value = Pair<A, B?>(it, second.value) }
        addSource(second) { value = Pair<A?, B>(first.value, it) }
    }
}

class TripleLiveData<A, B, C>(first: LiveData<A>, second: LiveData<B>, third: LiveData<C>) :
    MediatorLiveData<Triple<A?, B?, C?>>() {
    init {
        addSource(first) { value = Triple<A, B?, C?>(it, second.value, third.value) }
        addSource(second) { value = Triple<A?, B, C?>(first.value, it, third.value) }
        addSource(third) { value = Triple<A?, B?, C>(first.value, second.value, it) }
    }
}

fun <A, B> LiveData<A>.combine(other: LiveData<B>) =
    PairLiveData<A, B>(this, other)

fun <A, B, C> LiveData<A>.combine(second: LiveData<B>, third: LiveData<C>) =
    TripleLiveData<A, B, C>(this, second, third)
