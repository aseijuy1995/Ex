package edu.yujie.socketex.module

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketClientModule : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
//        val sf = String.format("%s onOpen() response = %s", ClientTAG, response.toString())
//        println(sf)
//        _socketStateFlow.value = SocketState.onClientOpen(sf)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
//        val sf = String.format("%s onMessage() text = %s", ClientTAG, text)
//        println(sf)
//        val chatBean = Gson().fromJson(text, ChatItem::class.java)
//        _socketStateFlow.value = SocketState.onClientMessage(sf, chatBean)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
//        val sf = String.format("%s onClosing() code = %d, reason = %s", ClientTAG, code, reason)
//        println(sf)
//        webSocketClient.close(1000, "close")
//        _socketStateFlow.value = SocketState.onClientClosing(sf)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
//        val sf = String.format("%s onClosed() code = %d, reason = %s", ClientTAG, code, reason)
//        println(sf)
//        _socketStateFlow.value = SocketState.onClientClosed(sf)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
//        val sf = String.format("%s onFailure() throwable = %s, response = %s", ClientTAG, t.toString(), response.toString())
//        println(sf)
//        _socketStateFlow.value = SocketState.onClientClosed(sf)
    }


//    fun startWebSocket(url: String) {
//        val ClientTAG = "Client"
//        webSocketClient = okHttpUtil.createWebSocket(url, object : WebSocketListener() {
//        })
//    }


}