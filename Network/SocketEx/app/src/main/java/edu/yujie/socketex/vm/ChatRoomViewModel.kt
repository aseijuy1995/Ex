package edu.yujie.socketex.vm

import android.app.Application
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.`interface`.IIntentRepo
import edu.yujie.socketex.`interface`.IRecorder
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.*
import edu.yujie.socketex.ext.BitmapCompress
import edu.yujie.socketex.ext.BitmapConfig
import edu.yujie.socketex.ext.compressStream
import edu.yujie.socketex.ext.getBitmap
import edu.yujie.socketex.socket.ChatBean
import edu.yujie.socketex.util.FileExt
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.util.createWebSocket
import edu.yujie.socketex.util.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
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

class ChatRoomViewModel(application: Application, private val repo: IIntentRepo, private val recorder: IRecorder) : BaseAndroidViewModel(application), KoinComponent {

    val inputEmptyState: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(true) }

    //camera
    private val mCameraLiveData: MutableLiveData<IntentResult> by lazy { MutableLiveData<IntentResult>() }
    val cameraLiveData: LiveData<IntentResult> = mCameraLiveData

    //album
    private val mAlbumLiveData: MutableLiveData<IntentResult> by lazy { MutableLiveData<IntentResult>() }
    val albumLiveData: LiveData<IntentResult> = mAlbumLiveData

    //camera
    fun createCameraBuilder(doStart: (builder: IntentBuilder) -> Unit) {
        val fileName = "Image_${System.nanoTime()}.jpg"
        val file = FileExt.createFile(context.externalCacheDir, fileName)
        repo.createCameraBuilder(IntentSetting.CameraSetting(context, file, doStart))
    }

    fun onCameraResult(result: IntentResult): IntentResult? = repo.onCameraResult(result)

    fun cameraResultEvent(intentResult: IntentResult) = mCameraLiveData.postValue(intentResult)

    //crop
    fun createCropBuilder(result: IntentResult, doStart: (builder: IntentBuilder) -> Unit) = repo.createCropBuilder(IntentSetting.CropSetting(result.uris?.first()!!, doStart = doStart))

    fun onCropResult(result: IntentResult): IntentResult? = repo.onCropResult(result)

    //album
    fun createAlbumBuilder(doStart: (builder: IntentBuilder) -> Unit) = repo.createAlbumBuilder(IntentSetting.AlbumSetting(doStart))

    fun onAlbumResult(result: IntentResult): IntentResult? = repo.onAlbumResult(result)

    fun albumResultEvent(result: IntentResult) = mAlbumLiveData.postValue(result)

    //mic
    private val mMicStateLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    val micStateLiveData: LiveData<Boolean> = mMicStateLiveData

    //recorder
    fun openMic() = mMicStateLiveData.postValue(true)

    var recorderStateRelay = BehaviorRelay.create<Boolean>()

    val errorRelay = BehaviorRelay.create<Boolean>()

    fun startRecorder() {
        val fileName = "Audio_${System.nanoTime()}.3gp"
        val file = FileExt.createFile(context.externalCacheDir, fileName)
        recorder.buildRecorder(RecorderSetting(contentResolver = context.contentResolver, file = file))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                recorder.startRecorder()
            }
        ////////////////////////////////////////////////

        Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {

            }


    }

    /**
     *
     * */

    fun stopRecorder() {
        recorder.stopRecorder()
        recorder.clearRecorder()
    }

    //
    //
    init {
        initViewEvent()
    }

    private fun initViewEvent() = viewModelScope.launch(Dispatchers.IO) {
        socketViewEvent
//            .receiveAsFlow()
            .consumeAsFlow()
            .collect {
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
                        it.uris.forEach {
                            val bitmap = it.getBitmap(context.contentResolver, BitmapConfig())
                            val stream = bitmap?.compressStream(BitmapCompress())
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
                    else -> {
                    }
                }
            }
    }

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

    //audio recorder
//    fun startRecorder() {
//        val fileName = "Audio_${System.nanoTime()}.3gp"
//        recorderFile = FileExt.createFile(context.externalCacheDir, fileName)
//        mediaRecorder = MediaRecorder().apply {
//            setAudioSource(MediaRecorder.AudioSource.MIC)
//            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//            setOutputFile(recorderFile?.absolutePath)
//            prepare()
//            start()
//        }
//        startTime = System.currentTimeMillis()
//        mRecorderStateFileLiveData.postValue(true to recorderFile)
//    }

//    fun stopRecorder(lessTime: (() -> Unit)? = null) {
//        stopTime = System.currentTimeMillis()
//        mediaRecorder?.apply {
//            stop()
//            reset()
//            release()
//            mRecorderStateFileLiveData.postValue(false to recorderFile)
//        }
//        if ((stopTime!! - startTime!!) / 1000 < 1) {
//            recorderFile?.let {
//                if (it.exists())
//                    it.delete()
//            }
//            lessTime?.invoke()
//            mRecorderStateFileLiveData.postValue(false to null)
//        }
//    }

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
        val file = FileExt.createFile(context.externalCacheDir, fileName)
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