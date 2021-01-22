package edu.yujie.socketex.module

import com.jakewharton.rxrelay3.PublishRelay
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MockWebServerModule : WebSocketListener() {

    //    private fun mockServer(listener: WebSocketListener) {
//        val mockWebServer = MockWebServer()
//        mockWebServer.enqueue(MockResponse().withWebSocketUpgrade(listener))
//        viewModelScope.launch(Dispatchers.IO) {
//            val hostName = mockWebServer.hostName
//            val port = mockWebServer.port
//            println("$TAG hostName = $hostName, port = $port")
//            val url = "ws://$hostName:$port"
//            webSocketUrlRelay.accept(url)
//        }
//    }
//
//    private fun startMockServer() {
//        val ServerTAG = "Server"
//
//        mockServer(object : WebSocketListener() {
//        })
//    }
    private val TAG = javaClass.simpleName

    private val informationRelay = PublishRelay.create<String>()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        val sf = String.format(
            "%s onOpen() response = %s\n" + "request header:%s\n" + "response header:%s",
            TAG, response.toString(), response.request.headers.toString(), response.headers.toString()
        )
        informationRelay.accept(sf)
//        _socketStateFlow.value = SocketState.onServerOpen(sf)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val sf = String.format("%s onMessage() text = %s", TAG, text)
        informationRelay.accept(sf)
//        _socketStateFlow.value = SocketState.onServerMessage(sf)
//
//        val json = convertBeanJson(text)
//        viewModelScope.launch(Dispatchers.IO) {
//            delay(1000L)
//            withContext(Dispatchers.Main) {
//                webSocket.send(json)
//            }
//        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        val sf = String.format("%s onClosing() code = %d, reason = %s", TAG, code, reason)
        informationRelay.accept(sf)
//        _socketStateFlow.value = SocketState.onServerClosing(sf)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        val sf = String.format("%s onClosed() code = %d, reason = %s", TAG, code, reason)
        informationRelay.accept(sf)
//        _socketStateFlow.value = SocketState.onServerClosed(sf)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        val sf = String.format("%s onFailure() throwable = %s, response = %s", TAG, t.toString(), response.toString())
        informationRelay.accept(sf)
//        _socketStateFlow.value = SocketState.onServerFailure(sf)
    }


}