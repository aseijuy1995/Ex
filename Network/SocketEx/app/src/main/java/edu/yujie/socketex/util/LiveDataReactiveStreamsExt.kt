package edu.yujie.socketex.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.rxjava3.core.*
import org.reactivestreams.Publisher


fun <T> Observable<T>.toLiveData(strategy: BackpressureStrategy) =
    LiveDataReactiveStreams.fromPublisher(this.toFlowable(strategy))

fun <T> Flowable<T>.toLiveData() =
    LiveDataReactiveStreams.fromPublisher(this)

fun <T> Single<T>.toLiveData() =
    LiveDataReactiveStreams.fromPublisher(this.toFlowable())

fun <T> Maybe<T>.toLiveData() =
    LiveDataReactiveStreams.fromPublisher(this.toFlowable())

fun <T> Completable.toLiveData() =
    LiveDataReactiveStreams.fromPublisher<T>(this.toFlowable())

fun <T> LiveData<T>.toPublisher(owner: LifecycleOwner): Publisher<T> =
    LiveDataReactiveStreams.toPublisher(owner, this)


