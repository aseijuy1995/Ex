package edu.yujie.socketex

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.yujie.retrofitex.OkHttpUtil
import edu.yujie.retrofitex.webSocket
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class ChatWorker(private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val TAG = javaClass.simpleName
    private var webSocket: WebSocket? = null
    private val url = "ws://echo.websocket.org"

    private val mInfoLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val infoLiveData: LiveData<String> = mInfoLiveData

    private val mChatsLiveData: MutableLiveData<MutableList<ChatBean>> by lazy { MutableLiveData<MutableList<ChatBean>>() }
    val chatsLiveData: LiveData<MutableList<ChatBean>> = mChatsLiveData

    override suspend fun doWork(): Result {
        webSocket = OkHttpUtil.get(context).webSocket(url, OkHttpWebSocketListener())
        return Result.success()
    }

    inner class OkHttpWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            println("$TAG onOpen()")
            mInfoLiveData.postValue("$TAG onOpen()")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println("$TAG onMessage() text")
            mInfoLiveData.postValue("$TAG onMessage()")

//            val chat = ChatBean(1, "Other", text)
//            val list = mChatsLiveData.value?.apply { add(chat) }
//            mChatsLiveData.postValue(list)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            println("$TAG onMessage() bytes")
            mInfoLiveData.postValue("$TAG onMessage()")

//            val chat = ChatBean(1, "Other", bytes.toString())
//            val list = mChatsLiveData.value?.apply { add(chat) }
//            mChatsLiveData.postValue(list)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            println("$TAG onClosing()")
            mInfoLiveData.postValue("$TAG onClosing()")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            println("$TAG onClosed()")
            mInfoLiveData.postValue("$TAG onClosed()")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            println("$TAG onFailure() t = ${t.message}")
            mInfoLiveData.postValue("$TAG onFailure()")
            t.printStackTrace()
        }


    }
}