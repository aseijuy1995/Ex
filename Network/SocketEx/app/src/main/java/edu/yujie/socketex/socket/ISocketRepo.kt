package edu.yujie.socketex.socket

import edu.yujie.socketex.bean.ChatItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow

interface ISocketRepo {

    fun fetchData(): Completable

    fun onOpen(): Completable

    fun onMessage(): BehaviorSubject<List<ChatItem>>

    fun onClose(): Completable

    fun onFailed(): Completable

    fun getChats(): Flow<List<ChatItem>>

    fun getChat(): Flow<ChatItem>

    fun getChatSync(): ChatItem?

    fun pushData(): Completable

}