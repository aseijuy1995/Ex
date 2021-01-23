package edu.yujie.socketex.listener

import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.inter.IWebSocketListener
import edu.yujie.socketex.inter.SocketInfo
import edu.yujie.socketex.inter.WebSocketState
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.util.createWebSocket
import io.reactivex.rxjava3.core.Observable
import okhttp3.WebSocket
import org.koin.core.KoinComponent
import org.koin.core.inject

class ClientSocketListener : IWebSocketListener(), KoinComponent {

    private val okHttpUtil by inject<OkHttpUtil>()

    private val webSocketRelay = PublishRelay.create<WebSocket>()

//    val chatItemRelay = PublishRelay.create<ChatItem>()

    fun execute(url: String): PublishRelay<WebSocket> {
        val webSocket = okHttpUtil.createWebSocket(url, this)
        webSocketRelay.accept(webSocket)
        println("ChatRoomViewModel:webSocketRelay:$url")
        return webSocketRelay
    }

    fun receiveChat(): Observable<ChatItem> = messageRelay.map {
        Gson().fromJson(it, ChatItem::class.java)
    }

    override fun receive(info: SocketInfo) {
        when (info.state) {
            WebSocketState.OPEN -> {

            }
            WebSocketState.MESSAGE -> {
//                val chatItem = Gson().fromJson(info.text, ChatItem::class.java)
//                chatItemRelay.accept(chatItem)
            }
            WebSocketState.CLOSING -> {
                info.webSocket.close(1000, "close")
            }
            WebSocketState.CLOSED -> {
            }
            WebSocketState.FAILURE -> {

            }
        }
    }

}