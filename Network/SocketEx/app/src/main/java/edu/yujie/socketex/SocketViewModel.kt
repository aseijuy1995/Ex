package edu.yujie.socketex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.WebSocketListener
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class SocketViewModel : ViewModel() {
    private val TAG = javaClass.simpleName
    private val mUrlLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val urlLiveData: LiveData<String> = mUrlLiveData

    fun mockServer(listener: WebSocketListener) {
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
}