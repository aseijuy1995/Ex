package edu.yujie.socketex

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import edu.yujie.socketex.databinding.ActivitySocketBinding
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.util.createWebSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import okio.Okio
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


//https://juejin.cn/post/6844903461796970504
//https://notfalse.net/7/three-way-handshake
//https://www.jianshu.com/p/089fb79e308b
//https://www.jianshu.com/p/8175f51e662c
//https://blog.csdn.net/xlh1191860939/article/details/103216735

//https://www.jianshu.com/p/45d27f3e1196
//https://www.jianshu.com/p/a6d086a3997d

class SocketActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val viewModel by viewModel<SocketViewModel>()
    private val okHttpUtil by inject<OkHttpUtil>()
    private val infoListAdapter = InfoListAdapter()
    private val infoList = mutableListOf<String>()
    private lateinit var webSocketClient: WebSocket
    private val chatListAdapter = ChatListAdapter()
    private val chatList = mutableListOf<ChatBean>()
    private val requestCode = 1001
    private lateinit var binding: ActivitySocketBinding
    //    private val url = "ws://echo.websocket.org"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySocketBinding>(this, R.layout.activity_socket)

        initView(binding)
        mockWebServer()

        val ClientTAG = "Client"
        viewModel.urlLiveData.observe(this) {
            webSocketClient = okHttpUtil.createWebSocket(it, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    val str = "$TAG $ClientTAG onOpen() response = $response"
                    refreshInfo(str)
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    val str = "$TAG $ClientTAG onMessage() text = $text"
                    refreshInfo(str)
                    val chatBean = Gson().fromJson(text, ChatBean::class.java)
                    refreshChat(chatBean)
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    super.onMessage(webSocket, bytes)
                    val str = "$TAG $ClientTAG onMessage() bytes = $bytes"
                    refreshInfo(str)
                    val byteArray = bytes.toByteArray()
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    binding.ivImg.setImageBitmap(bitmap)
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosing(webSocket, code, reason)
                    val str = "$TAG $ClientTAG onClosing() code = $code, reason = $reason"
                    refreshInfo(str)
                    webSocketClient.close(1000, "close")
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    val str = "$TAG $ClientTAG onClosed() code = $code, reason = $reason"
                    refreshInfo(str)

                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    val str = "$TAG $ClientTAG onFailure() throwable = $t, response = $response"
                    refreshInfo(str)
                }
            })
        }

        binding.btnView.clicks().subscribe {
            val text = binding.etText.text.toString().trim()
            if (TextUtils.isEmpty(text)) {
                Snackbar.make(binding.btnView, "Can not empty!", Snackbar.LENGTH_SHORT).show()
            } else {
                val chatBean = ChatBean(0, "Me", text)
                val json = Gson().toJson(chatBean)
                refreshChat(chatBean)
                println("$TAG json = $json")
                webSocketClient.send(json)
//                webSocketClient.send()
                binding.etText.setText("")
            }
        }

        binding.btnImg.clicks()
            .compose(
                RxPermissions(this)
                    .ensure(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
            )
            .subscribe {
                startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), requestCode)
            }

    }

    private fun refreshChat(chatBean: ChatBean) {
        lifecycleScope.launch(Dispatchers.Main) {
            chatList.add(chatBean)
            chatListAdapter.submitList(chatList)
            binding.rvView.scrollToPosition(chatList.size)
        }
    }

    private fun refreshInfo(str: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            println("refreshInfo:$str")
            infoList.add(str)
            infoListAdapter.submitList(infoList)
            binding.rvInfo.scrollToPosition(infoList.size)
        }
    }

    private fun initView(binding: ActivitySocketBinding) {
        binding.rvInfo.apply {
            layoutManager = LinearLayoutManager(this@SocketActivity)
            adapter = infoListAdapter
        }
        binding.rvView.apply {
            layoutManager = LinearLayoutManager(this@SocketActivity)
            adapter = chatListAdapter
        }
    }

    private fun mockWebServer() {
        val ServerTAG = "MockWebServer"

        viewModel.mockServer(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                val str = "$TAG:$ServerTAG onOpen() response = $response"
                refreshInfo(str)
                println("$TAG:$ServerTAG request header:${response.request.headers}")
                println("$TAG:$ServerTAG response header:${response.headers}")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val str = "$TAG:$ServerTAG onMessage() text = $text"
                refreshInfo(str)
                val chatBean = Gson().fromJson(text, ChatBean::class.java)
                val chatBeanOther = ChatBean(-1, "Ohter", "${chatBean.msg} - From Server")
                val json = Gson().toJson(chatBeanOther)
                webSocket.send(json)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                val str = "$TAG:$ServerTAG onMessage() bytes = $bytes"
                refreshInfo(str)
                webSocket.send(bytes)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                val str = "$TAG:$ServerTAG onClosing() code = $code, reason = $reason"
                refreshInfo(str)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                val str = "$TAG:$ServerTAG onClosed() code = $code, reason = $reason"
                refreshInfo(str)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                val str = "$TAG:$ServerTAG onFailure() throwable = $t, response = $response"
                refreshInfo(str)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            this.requestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    val url = data?.data!!
                    val byteArray = contentResolver.openInputStream(url)?.buffered().use {
                        it?.readBytes()
                    }
                    val byteString = byteArray?.toByteString(0, byteArray.size)!!

//                    val bitmap = (data.extras?.get("data") as Bitmap)
//                    val baos = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//                    val byteString = baos.toByteArray().toByteString()
                    webSocketClient.send(byteString)

                }
            }
        }

    }
}