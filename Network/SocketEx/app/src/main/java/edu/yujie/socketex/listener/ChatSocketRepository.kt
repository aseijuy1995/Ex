package edu.yujie.socketex.listener

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.ChatItem
import io.reactivex.rxjava3.core.Observable
import okhttp3.WebSocket

class ChatSocketRepository : IChatSocketRepository {

    private val mockServer: MockServerSocketListener = MockServerSocketListener()

    private val clientSocket: ClientSocketListener = ClientSocketListener()

    override fun executeMockServer(): BehaviorRelay<String> = mockServer.execute()

    override fun executeClientSocket(url: String): BehaviorRelay<WebSocket> = clientSocket.execute(url)

    override fun receiveInfoFromMockServer(): PublishRelay<String> = mockServer.informationRelay

    override fun receiveInfoFromClient(): PublishRelay<String> = clientSocket.informationRelay

    override fun receiveInfo(): Observable<String> = receiveInfoFromClient().mergeWith(receiveInfoFromMockServer())

    override fun receiveChat(): Observable<ChatItem> = clientSocket.receiveChat()

}