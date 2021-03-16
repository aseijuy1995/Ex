package tw.north27.coachingapp.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class PairLiveData<A, B>(first: LiveData<A>, second: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(first) { value = it to second.value }
        addSource(second) { value = first.value to it }
    }
}

class TripleLiveData<A, B, C>(first: LiveData<A>, second: LiveData<B>, third: LiveData<C>) : MediatorLiveData<Triple<A?, B?, C?>>() {
    init {
        addSource(first) { value = Triple(it, second.value, third.value) }
        addSource(second) { value = Triple(first.value, it, third.value) }
        addSource(third) { value = Triple(first.value, second.value, it) }
    }
}

data class Quad<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

class QuadLiveData<A, B, C, D>(first: LiveData<A>, second: LiveData<B>, third: LiveData<C>, fourth: LiveData<D>) : MediatorLiveData<Quad<A?, B?, C?, D?>>() {
    init {
        addSource(first) { value = Quad(it, second.value, third.value, fourth.value) }
        addSource(second) { value = Quad(first.value, it, third.value, fourth.value) }
        addSource(third) { value = Quad(first.value, second.value, it, fourth.value) }
        addSource(fourth) { value = Quad(first.value, second.value, third.value, it) }
    }
}

fun <A, B> LiveData<A>.combine(other: LiveData<B>): PairLiveData<A, B> {
    return PairLiveData(this, other)
}

fun <A, B, C> LiveData<A>.combine(second: LiveData<B>, third: LiveData<C>): TripleLiveData<A, B, C> {
    return TripleLiveData(this, second, third)
}

fun <A, B, C, D> LiveData<A>.combine(second: LiveData<B>, third: LiveData<C>, fourth: LiveData<D>): QuadLiveData<A, B, C, D> {
    return QuadLiveData(this, second, third, fourth)
}