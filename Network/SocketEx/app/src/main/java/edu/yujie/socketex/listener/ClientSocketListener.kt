package edu.yujie.socketex.listener

import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.finish.util.OkHttpUtil
import edu.yujie.socketex.finish.util.createWebSocket
import io.reactivex.rxjava3.core.Observable
import okhttp3.WebSocket
import org.koin.core.KoinComponent
import org.koin.core.inject

class ClientSocketListener : IWebSocketListener(), KoinComponent {

    private val okHttpUtil by inject<OkHttpUtil>()

    private val webSocketRelay = BehaviorRelay.create<WebSocket>()

    fun execute(url: String): BehaviorRelay<WebSocket> {
        val webSocket = okHttpUtil.createWebSocket(url, this)
        webSocketRelay.accept(webSocket)
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