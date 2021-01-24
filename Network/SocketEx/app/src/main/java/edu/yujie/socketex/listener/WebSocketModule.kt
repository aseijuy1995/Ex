package edu.yujie.socketex.listener

import com.jakewharton.rxrelay3.PublishRelay
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

abstract class IWebSocketListener : WebSocketListener() {

    val TAG = javaClass.simpleName

    val informationRelay = PublishRelay.create<String>()

    val messageRelay = PublishRelay.create<String>()

    abstract fun receive(info: SocketInfo)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        val sf = String.format(
            "%s onOpen() response = %s\n" + "request header:%s\n" + "response header:%s",
            TAG, response.toString(), response.request.headers.toString(), response.headers.toString()
        )
        informationRelay.accept(sf)
        receive(SocketInfo(state = WebSocketState.OPEN, webSocket = webSocket, response = response))
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        val sf = String.format("%s onMessage() bytes = %s", TAG, bytes)
        informationRelay.accept(sf)
        receive(SocketInfo(state = WebSocketState.MESSAGE, webSocket = webSocket, bytes = bytes))
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val sf = String.format("%s onMessage() text = %s", TAG, text)
        informationRelay.accept(sf)
        messageRelay.accept(text)
        receive(SocketInfo(state = WebSocketState.MESSAGE, webSocket = webSocket, text = text))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        val sf = String.format("%s onClosing() code = %d, reason = %s", TAG, code, reason)
        informationRelay.accept(sf)
        receive(SocketInfo(state = WebSocketState.CLOSING, webSocket = webSocket, code = code, reason = reason))
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        val sf = String.format("%s onClosed() code = %d, reason = %s", TAG, code, reason)
        informationRelay.accept(sf)
        receive(SocketInfo(state = WebSocketState.CLOSED, webSocket = webSocket, code = code, reason = reason))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        val sf = String.format("%s onFailure() throwable = %s, response = %s", TAG, t.toString(), response.toString())
        informationRelay.accept(sf)
        receive(SocketInfo(state = WebSocketState.FAILURE, webSocket = webSocket, t = t, response = response))
    }
}

enum class WebSocketState {
    OPEN, MESSAGE, CLOSING, CLOSED, FAILURE
}

data class SocketInfo(
    val webSocket: WebSocket,
    val response: Response? = null,
    val bytes: ByteString? = null,
    val text: String? = null,
    val code: Int? = null,
    val reason: String? = null,
    val t: Throwable? = null,
    val state: WebSocketState
)