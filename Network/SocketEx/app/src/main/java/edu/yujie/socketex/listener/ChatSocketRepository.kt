package edu.yujie.socketex.listener

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.ChatItem
import io.reactivex.rxjava3.core.Observable
import okhttp3.WebSocket

class ChatSocketRepository : IChatSocketRepository {

    private val mockServer: MockServerSocketListener = MockServerSocketListener()

    private val clientSocket: ClientSocketListener = ClientSocketListener()

    private val urlRelay: BehaviorRelay<String> = BehaviorRelay.create()

    private val webSocketRelay: BehaviorRelay<WebSocket> = BehaviorRelay.create()

    override fun executeMockServer(): Observable<String> {
        if (urlRelay.value == null)
            mockServer.execute()
                .map { urlRelay.accept(it) }
                .subscribe()

        return urlRelay
    }

    override fun executeClientSocket(url: String): Observable<WebSocket> {
        if (webSocketRelay.value == null) {
            clientSocket.execute(url)
                .map { webSocketRelay.accept(it) }
                .subscribe()
        }
        return webSocketRelay


        //
        //
        //
//        return mockServer.execute()
//            .map {
//                println("ChatRoomViewModel:execute:str2: $it")
//                it
//            }
//            .flatMap {
//                println("ChatRoomViewModel:execute:str: $it")
//                clientSocket.execute(it)
//            }
    }


    override fun receiveInfoFromMockServer(): PublishRelay<String> {
        return mockServer.informationRelay
    }

    override fun receiveInfoFromClient(): PublishRelay<String> {
        println("ChatRoomViewModel:receiveInfoFromClient:${clientSocket.informationRelay.hashCode()}")
        return clientSocket.informationRelay
    }

    override fun receiveInfo(): Observable<String> {
        println("ChatRoomViewModel:receiveInfo")
        return receiveInfoFromClient().mergeWith(receiveInfoFromMockServer())
    }

    //    override fun receiveChat(): PublishRelay<ChatItem> = clientSocket.chatItemRelay
    override fun receiveChat(): Observable<ChatItem> = clientSocket.receiveChat()

}