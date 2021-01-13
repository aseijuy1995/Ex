package edu.yujie.socketex.vm

import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.ChatImgBean
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.ext.calculateInSampleSize
import edu.yujie.socketex.ext.compressStream
import edu.yujie.socketex.repo.IntentRepoImpl
import edu.yujie.socketex.socket.ChatBean
import edu.yujie.socketex.util.*
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
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.io.OutputStream

class ChatRoomViewModel(application: Application) : BaseAndroidViewModel(application), KoinComponent {
    val inputEmptyState: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(true) }
    //
    val repo = IntentRepoImpl()
    //
    init {
        println("startCamera:init")
        val fileName = "Image_${System.nanoTime()}.jpg"
        val file = FileUtil.createFile(context.externalCacheDir, fileName)
        repo.startCamera(IntentSetting(file)).subscribe {
            
        }
    }

    private var captureUri: Uri? = null

    //capture
    private val mCaptureUrlLiveData: MutableLiveData<Uri> by lazy { MutableLiveData<Uri>() }
    val captureUrlLiveData: LiveData<Uri> = mCaptureUrlLiveData

    //album
    private val mAlbumLiveData: MutableLiveData<MutableList<Uri>> by lazy { MutableLiveData<MutableList<Uri>>() }
    val albumLiveData: LiveData<MutableList<Uri>> = mAlbumLiveData

    //audio
    private val mMicDisplayLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val micDisplayLiveData: LiveData<Boolean> = mMicDisplayLiveData

    //audio recorder
    private var recorderFile: File? = null
    private var mediaRecorder: MediaRecorder? = null

    private val mRecorderStateFileLiveData: MutableLiveData<Pair<Boolean, File?>> by lazy { MutableLiveData<Pair<Boolean, File?>>(false to null) }
    val recorderStateFileLiveData: LiveData<Pair<Boolean, File?>> = mRecorderStateFileLiveData

    private var startTime: Long? = null
    private var stopTime: Long? = null

    private var mediaPlayer: MediaPlayer? = null

    val socketViewEvent = Channel<SocketViewEvent>(Channel.UNLIMITED)

    val mMediaPlayerState: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val mediaPlayerState: LiveData<Boolean> = mMediaPlayerState

    init {
        initViewEvent()
    }

    private fun initViewEvent() = viewModelScope.launch(Dispatchers.IO) {
        socketViewEvent.consumeAsFlow().collect {
            when (it) {
                is SocketViewEvent.SendText -> {
                    if (TextUtils.isEmpty(it.str)) {
                        mSocketState.value = SocketState.ShowEmptyText("Can not empty!")
                    } else {
                        val chatBean = ChatBean(id = 0, name = "Me", msg = it.str, isOneSelf = true, time = getTime())
                        val json = Gson().toJson(chatBean)
                        println("$TAG json = $json")
                        webSocketClient.send(json)
                        mSocketState.value = SocketState.ShowChat(chatBean = chatBean)
                    }
                }

                is SocketViewEvent.SendImg -> {
                    val imgBytes = mutableListOf<ByteArray?>()
                    it.uriList.forEach {
                        val bitmap = it.calculateInSampleSize(context, 2)
                        val stream = bitmap?.compressStream()
//                        val stream = ByteArrayInputStream(baos.toByteArray())
                        //
//                        val stream = context.contentResolver.openInputStream(it)

//                        val buffer = ByteArray(2048)
                        val byteArray = stream?.readBytes()
//                        val byteArray = stream?.buffered()?.use {
//                            val red = it.read(buffer)
//                            if (red >= 0) {
//                                buffer.copyOf(red)
//                            } else {
//                                stream.close()
//                                null
//                            }
//                        }
                        imgBytes.add(byteArray)
                    }

                    val chatImgList = imgBytes.mapIndexed { index, bytes ->
                        bytes?.let {
                            ChatImgBean(index, it)
                        }
                    }
                    val chatBean = ChatBean(id = 0, name = "Me", isOneSelf = true, time = getTime(), imgBytes = chatImgList)
                    val json = Gson().toJson(chatBean)
                    println("$TAG json:$json")
                    webSocketClient.send(json)
                    mSocketState.value = SocketState.ShowChat(chatBean = chatBean)
                }

                is SocketViewEvent.SendRecorder -> {
                    val chatBean = ChatBean(id = 0, name = "Me", isOneSelf = true, time = getTime(), recorderBytes = it.file.readBytes())
                    val json = Gson().toJson(chatBean)
                    println("$TAG json:$json")
                    webSocketClient.send(json)
                    mSocketState.value = SocketState.ShowChat(chatBean = chatBean)
                }
            }
        }
    }

    //capture
    fun startCapture(fileName: String, call: (Intent) -> Unit) {
        val file = FileUtil.createFile(context.externalCacheDir, fileName)
        captureUri = context.getContentUri(file)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, captureUri)
        }
        call(intent)
    }

    fun resultCapture() {
        captureUri?.let {
            mCaptureUrlLiveData.postValue(it)
        }
    }

    //album
    fun startAlbum(call: (Intent) -> Unit) {
//        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
        call(intent)
    }

    fun resultAlbum(data: Intent?) {
        val albumUriList = mutableListOf<Uri>()
        data?.clipData?.let {
            for (i in 0 until it.itemCount) {
                val albumUri = it.getItemAt(i).uri
                albumUriList.add(albumUri)
                albumUri.calculateInSampleSize(context, 2)
//                getImageWidthHeight(albumUri)
            }
        }
        data?.data?.let { albumUri ->
            albumUriList.add(albumUri)
            albumUri.calculateInSampleSize(context, 2)
//            getImageWidthHeight(albumUri)
        }

        mAlbumLiveData.postValue(albumUriList)
    }


    private fun getImageWidthHeight(uri: Uri) {
//        Glide.with(context).asBitmap().load(uri).into(object : SimpleTarget<Bitmap>() {
//            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
//                val width = bitmap.getWidth()
//                val height = bitmap.getHeight()
//                println("width=$width, height=$height")
//            }
//        })


        val options = BitmapFactory.Options()
        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)
        options.inJustDecodeBounds = true
        options.inSampleSize = 2

        val width = options.outWidth
        val height = options.outHeight

        options.inJustDecodeBounds = false
        val bitmap2 = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options)

        val width2 = options.outWidth
        val height2 = options.outHeight
        println("width:$width, height:$height")
        println("bitmap2: width:${bitmap2?.width}, height:${bitmap2?.height}, width2:$width2, height2:$height2")
    }
    //

    fun openMic() = mMicDisplayLiveData.postValue(true)

    //audio recorder
    fun startRecorder() {
        val fileName = "Audio_${System.nanoTime()}.3gp"
        recorderFile = FileUtil.createFile(context.externalCacheDir, fileName)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recorderFile?.absolutePath)
            prepare()
            start()
        }
        startTime = System.currentTimeMillis()
        mRecorderStateFileLiveData.postValue(true to recorderFile)
    }

    fun stopRecorder(lessTime: (() -> Unit)? = null) {
        stopTime = System.currentTimeMillis()
        mediaRecorder?.apply {
            stop()
            reset()
            release()
            mRecorderStateFileLiveData.postValue(false to recorderFile)
        }
        if ((stopTime!! - startTime!!) / 1000 < 1) {
            recorderFile?.let {
                if (it.exists())
                    it.delete()
            }
            lessTime?.invoke()
            mRecorderStateFileLiveData.postValue(false to null)
        }
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

    fun startPlayer(byteArray: ByteArray) {
        val fileName = "Audio_${System.nanoTime()}.3gp"
        val file = FileUtil.createFile(context.externalCacheDir, fileName)
        file.writeBytes(byteArray)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file.absolutePath)
            prepare()
        }
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                mMediaPlayerState.postValue(true)
            }
        }

    }

    fun stopPlayer() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.reset()
                it.stop()
                it.release()
                mediaPlayer = null
            }
            mMediaPlayerState.postValue(true)
        }
    }

    //


    private val mUrlLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val urlLiveData: LiveData<String> = mUrlLiveData

    private val mSocketState = MutableStateFlow<SocketState>(SocketState.Idle)
    val socketState: StateFlow<SocketState> = mSocketState

    private val infoList = mutableListOf<String>()
    private val mInfoListLiveData: MutableLiveData<MutableList<String>> by lazy { MutableLiveData<MutableList<String>>(infoList) }
    val infoListLiveData: LiveData<MutableList<String>> = mInfoListLiveData

    private lateinit var webSocketClient: WebSocket
    private val okHttpUtil by inject<OkHttpUtil>()

    private val chatList = mutableListOf<ChatBean>()
    private val mChatListLiveData: MutableLiveData<MutableList<ChatBean>> by lazy { MutableLiveData<MutableList<ChatBean>>(chatList) }
    val chatListLiveData: LiveData<MutableList<ChatBean>> = mChatListLiveData

    private val mRecordLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }
    val recordLiveData: LiveData<Boolean> = mRecordLiveData

    private var file: File? = null
    private var outStream: OutputStream? = null


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

    fun addInfo(str: String) {
        infoList.add(str)
        mInfoListLiveData.value = infoList
    }

    fun addChat(chatBean: ChatBean) {
        chatList.add(chatBean)
        mChatListLiveData.value = chatList
    }

    private fun convertBeanJson(text: String): String {
        val chatBean = Gson().fromJson(text, ChatBean::class.java)
        val msg = if (chatBean.msg != null) "${chatBean.msg} - From Server" else null
        val imgBytes = chatBean.imgBytes
        val recorderBytes = chatBean.recorderBytes
        val chatBeanOther = ChatBean(id = -1, name = "Ohter", msg = msg, isOneSelf = false, time = getTime(), imgBytes = imgBytes, recorderBytes = recorderBytes)
        val json = Gson().toJson(chatBeanOther)
        return json
    }
}