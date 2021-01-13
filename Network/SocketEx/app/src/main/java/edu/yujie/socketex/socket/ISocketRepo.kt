package edu.yujie.socketex.socket

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow

interface ISocketRepo {

    fun fetchData(): Completable

    fun onOpen(): Completable

    fun onMessage(): BehaviorSubject<List<ChatBean>>

    fun onClose(): Completable

    fun onFailed(): Completable

    fun getChats(): Flow<List<ChatBean>>

    fun getChat(): Flow<ChatBean>

    fun getChatSync(): ChatBean?

    fun pushData(): Completable

}