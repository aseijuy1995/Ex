package edu.yujie.socketex

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.util.createWebSocket
import edu.yujie.socketex.util.getTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.ByteString
import okio.ByteString.Companion.toByteString
import org.koin.core.KoinComponent
import org.koin.core.inject

class SocketViewModel : ViewModel(), KoinComponent {
    private val TAG = javaClass.simpleName

    private val mUrlLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val urlLiveData: LiveData<String> = mUrlLiveData

    private val mSocketState = MutableStateFlow<SocketState>(SocketState.Idle)
    val socketState: StateFlow<SocketState> = mSocketState

    private val infoList = mutableListOf<String>()
    private val mInfoListLiveData: MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>(infoList) }
    val infoListLiveData: LiveData<MutableList<String>> = mInfoListLiveData

    val socketViewEvent = Channel<SocketViewEvent>(Channel.UNLIMITED)

    private lateinit var webSocketClient: WebSocket
    private val okHttpUtil by inject<OkHttpUtil>()

    private val chatList = mutableListOf<ChatBean>()
    private val mChatListLiveData: MutableLiveData<MutableList<ChatBean>> by lazy { MutableLiveData<MutableList<ChatBean>>(chatList) }
    val chatListLiveData: LiveData<MutableList<ChatBean>> = mChatListLiveData

    init {
        initSocketViewEvent()
    }

    private fun initSocketViewEvent() = viewModelScope.launch(Dispatchers.IO) {
        socketViewEvent.consumeAsFlow().collect {
            when (it) {
                is SocketViewEvent.SendClick -> {
                    if (TextUtils.isEmpty(it.str)) {
                        mSocketState.value = SocketState.ShowEmptyText("Can not empty!")
                    } else {
                        val chatBean = ChatBean(id = 0, name = "Me", msg = it.str, isOneSelf = true, time = getTime())
                        val json = Gson().toJson(chatBean)
                        println("$TAG json = $json")
                        webSocketClient.send(json)
                        mSocketState.value = SocketState.ShowMsg(chatBean = chatBean)
                    }
                }
            }
        }
    }


    fun addInfo(str: String) {
        infoList.add(str)
        mInfoListLiveData.value = infoList
    }

    fun addChat(chatBean: ChatBean) {
        chatList.add(chatBean)
        mChatListLiveData.value = chatList
    }

    private fun mockServer(listener: WebSocketListener) {
        val mockWebServer = MockWebServer()
        mockWebServer.enqueue(MockResponse().withWebSocketUpgrade(listener))
        viewModelScope.launch(Dispatchers.IO) {
            val hostName = mockWebServer.hostName
            val port = mockWebServer.port
            println("$TAG hostName = $hostName, port = $port")
            val url = "ws://$hostName:$port"
            mUrlLiveData.postValue(url)
        }
    }

    fun startMockServer(): StateFlow<SocketState> {
        val ServerTAG = "Server"

        mockServer(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val sf = String.format(
                    "%s onOpen() response = %s\n" +
                            "request header:%s\n" +
                            "response header:%s",
                    ServerTAG, response.toString(), response.request.headers, response.headers
                )
                println(sf)
                mSocketState.value = SocketState.onServerOpen(sf)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val sf = String.format("%s onMessage() text = %s", ServerTAG, text)
                println(sf)
                mSocketState.value = SocketState.onServerMessage(sf)

                val json = convertBeanJson(text)
                viewModelScope.launch(Dispatchers.IO) {
                    delay(1000L)
                    withContext(Dispatchers.Main) {
                        webSocket.send(json)
                    }
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                val sf = String.format("%s onMessage() bytes = %s", ServerTAG, ByteString.toString())
                println(sf)
                mSocketState.value = SocketState.onServerMessage(sf)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                val sf = String.format("%s onClosing() code = %d, reason = %s", ServerTAG, code, reason)
                println(sf)
                mSocketState.value = SocketState.onServerClosing(sf)

            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                val sf = String.format("%s onClosed() code = %d, reason = %s", ServerTAG, code, reason)
                println(sf)
                mSocketState.value = SocketState.onServerClosed(sf)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                val sf = String.format("%s onFailure() throwable = %s, response = %s", ServerTAG, t.toString(), response.toString())
                println(sf)
                mSocketState.value = SocketState.onServerFailure(sf)
            }
        })
        return socketState
    }

    fun startWebSocket(url: String): WebSocket {
        val ClientTAG = "Client"
        webSocketClient = okHttpUtil.createWebSocket(url, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val sf = String.format("%s onOpen() response = %s", ClientTAG, response.toString())
                println(sf)
                mSocketState.value = SocketState.onClientOpen(sf)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val sf = String.format("%s onMessage() text = %s", ClientTAG, text)
                println(sf)
                val chatBean = Gson().fromJson(text, ChatBean::class.java)
                mSocketState.value = SocketState.onClientMessage(sf, chatBean)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                val sf = String.format("%s onClosing() code = %d, reason = %s", ClientTAG, code, reason)
                println(sf)
                webSocketClient.close(1000, "close")
                mSocketState.value = SocketState.onClientClosing(sf)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                val sf = String.format("%s onClosed() code = %d, reason = %s", ClientTAG, code, reason)
                println(sf)
                mSocketState.value = SocketState.onClientClosed(sf)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                val sf = String.format("%s onFailure() throwable = %s, response = %s", ClientTAG, t.toString(), response.toString())
                println(sf)
                mSocketState.value = SocketState.onClientClosed(sf)
            }
        })
        return webSocketClient
    }

    private fun convertBeanJson(text: String): String {
        val chatBean = Gson().fromJson(text, ChatBean::class.java)
        val msg = if (chatBean.msg != null) "${chatBean.msg} - From Server" else null
        val imgByte = chatBean.imgByte
        val chatBeanOther = ChatBean(id = -1, name = "Ohter", msg = msg, isOneSelf = false, time = getTime(), imgByte = imgByte)
        val json = Gson().toJson(chatBeanOther)
        return json
    }

    fun convertByteBean(byteArray: ByteArray?): ChatBean {
        val byteString = byteArray?.toByteString(0, byteArray.size)!!
        val chatBean = ChatBean(id = 0, name = "Me", isOneSelf = true, time = getTime(), imgByte = byteString)
        return chatBean
    }
}