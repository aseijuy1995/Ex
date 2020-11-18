package com.example.websockerext

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.websockerext.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.ByteString
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var wsClient: WebSocket? = null
    private var wsServer: WebSocket? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                act = this@MainActivity
            }

//        //init Server
//        val server = mockServer()
//        //client
//        initClient(server)


        //api
//        lifecycleScope.launch(Dispatchers.IO) {
//            val appresult =
//                RetrofitManager.getInstance(this@MainActivity).iApiService.postAppResultDef()
//                    .await()
//            println("appresult:applestoreurl:${appresult.applestoreurl}, result:${appresult.result}")
//        }

    }

    fun serverClick(v: View) {
        if (TextUtils.isEmpty(binding?.etServer?.text))
            Toast.makeText(this, "Cannot Empty", Toast.LENGTH_SHORT).show()
        else {
            wsServer?.send(binding?.etServer?.text.toString())
            binding?.etServer?.text = null
        }
    }

    fun clientClick(v: View) {
        if (TextUtils.isEmpty(binding?.etClient?.text))
            Toast.makeText(this, "Cannot Empty", Toast.LENGTH_SHORT).show()
        else {
            wsClient?.send(binding?.etClient?.text.toString())
            binding?.etClient?.text = null
        }
    }

//    private var wsClient: WebSocket? = null
//    private var wsServer: WebSocket? = null

    fun initClient(server: MockWebServer) {
        lifecycleScope.launch(Dispatchers.IO) {
            val hostName = server.hostName
            val port = server.port
            println("hostName:$hostName")
            println("port:$port")
            val url = server.url("/")
//        val url = "ws://$hostName:$port/"
            val client = OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder()
                .url(url)
                .build()

            client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    wsClient = webSocket
                    println("client:onOpen")
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    super.onMessage(webSocket, bytes)
                    println("client:onMessage:byte:${bytes}")
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    println("client:onMessage:$text")
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    println("client:onClosed")
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosing(webSocket, code, reason)
                    println("client:onClosing")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    println("client:onFailure")

                }
            })
        }
    }

    fun mockServer(): MockWebServer {
        val TAG = "MockWebServer"
        val server = MockWebServer()
        val response = MockResponse().withWebSocketUpgrade(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                wsServer = webSocket
                println("$TAG:Server:onOpen()")
                println("$TAG:Server:request header${response.request.headers}")
                println("$TAG:Server:response header:${response.headers}}")
                println("$TAG:Server:response:${response}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                println("$TAG:Server:onMessage()")
                println("$TAG:Server:msg:$text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                println("$TAG:Server:onMessage()")
                println("$TAG:Server:msg:bytes:$bytes")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                println("$TAG:Server:onClosed()")
                println("$TAG:Server:code:$code,reason:$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                println("$TAG:Server:onFailure()")
                println("$TAG:Server:Throwable:$t")
                println("$TAG:Server:Response:$response")
            }
        })
        server.enqueue(response)
        return server
    }
}