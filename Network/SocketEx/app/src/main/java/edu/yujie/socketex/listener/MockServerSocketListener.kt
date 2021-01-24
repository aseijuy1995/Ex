package edu.yujie.socketex.listener

import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.bean.ChatSender
import edu.yujie.socketex.util.getTime
import kotlinx.coroutines.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlin.coroutines.CoroutineContext

class MockServerSocketListener : IWebSocketListener(), CoroutineScope {

    lateinit var mockWebServer: MockWebServer

    private val urlRelay = BehaviorRelay.create<String>()

    fun execute(): BehaviorRelay<String> {
        mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().withWebSocketUpgrade(this))
        launch(Dispatchers.IO) {
            val hostName = mockWebServer.hostName
            val port = mockWebServer.port
            println("$TAG hostName = $hostName, port = $port")
            urlRelay.accept("ws://$hostName:$port")
        }
        return urlRelay
    }

    override fun receive(info: SocketInfo) {
        when (info.state) {
            WebSocketState.OPEN -> {

            }
            WebSocketState.MESSAGE -> {
                val json = convertJsonData(info.text!!)
                launch(Dispatchers.IO) {
                    delay(1000L)
                    withContext(Dispatchers.Main) {
                        info.webSocket.send(json)
                    }
                }
            }
            WebSocketState.CLOSING -> {

            }
            WebSocketState.CLOSED -> {
                job.cancel()
            }
            WebSocketState.FAILURE -> {

            }
        }
    }

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
            println("$TAG Throwable: ${t.message} ")
        }

    //轉換Json數據
    fun convertJsonData(text: String): String {
        val chatBean = Gson().fromJson(text, ChatItem::class.java)
        val msg = if (chatBean.textMsg != null) "${chatBean.textMsg} - From Server" else null
        val imgBytes = chatBean.imgListMsg
        val recorderBytes = chatBean.audioMsg
        val chatBeanOther = ChatItem(id = -1, name = "Ohter", textMsg = msg, sender = ChatSender.OTHER, time = getTime(), imgListMsg = imgBytes, audioMsg = recorderBytes)
        val json = Gson().toJson(chatBeanOther)
        return json
    }

}