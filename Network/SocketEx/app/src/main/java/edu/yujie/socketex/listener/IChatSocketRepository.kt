package edu.yujie.socketex.listener

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.ChatItem
import io.reactivex.rxjava3.core.Observable
import okhttp3.WebSocket

interface IChatSocketRepository {

    fun executeMockServer(): BehaviorRelay<String>

    fun executeClientSocket(url: String): BehaviorRelay<WebSocket>

    fun receiveInfoFromMockServer(): PublishRelay<String>

    fun receiveInfoFromClient(): PublishRelay<String>

    fun receiveInfo(): Observable<String>

    fun receiveChat(): Observable<ChatItem>
}