package edu.yujie.socketex.vm

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.*
import edu.yujie.socketex.ext.BitmapCompress
import edu.yujie.socketex.ext.BitmapConfig
import edu.yujie.socketex.ext.compressToByteArray
import edu.yujie.socketex.ext.getBitmap
import edu.yujie.socketex.impl.IRecordingRepo
import edu.yujie.socketex.inter.IIntentRepo
import edu.yujie.socketex.listener.ChatSocketRepository
import edu.yujie.socketex.util.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.WebSocket
import java.io.File

class ChatRoomViewModel(application: Application, private val recordingRepo: IRecordingRepo, private val repo: IIntentRepo) : BaseAndroidViewModel(application) {

    val chatSocketRepository = ChatSocketRepository()

    //--------------------------------------------------------------------------------------
    //info & chat
    private val infoList = mutableListOf<String>()

    private val _infoList = mutableLiveData(infoList)

    val infoListLiveData = _infoList.asLiveData()

    private val chatList = mutableListOf<ChatItem>()

    private val _chatList = mutableLiveData(chatList)

    val chatListLiveData = _chatList.asLiveData()

    private val _toast = mutableLiveData<String>("")

    val toast = _toast.asLiveData()

    lateinit var webSocket: WebSocket

    init {
        execute()
        receiveInfoList()
        receiveChatList()
    }

    fun execute() = chatSocketRepository.executeMockServer()
        .flatMap { chatSocketRepository.executeClientSocket(it) }
        .subscribe { webSocket = it }
        .addTo(compositeDisposable = compositeDisposable)

    fun receiveInfoList() = chatSocketRepository.receiveInfo()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { addInfo(it) }
        .addTo(compositeDisposable = compositeDisposable)

    private fun addInfo(str: String) = infoList.also {
        it.add(str)
        _infoList.value = it
    }

    fun receiveChatList() = chatSocketRepository.receiveChat()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            addChat(it)
        }
        .addTo(compositeDisposable = compositeDisposable)

    fun addChat(chatItem: ChatItem) = chatList.also {
        it.add(chatItem)
//        _chatList.postValue(it)
        _chatList.value = it
    }

    fun sendText(str: String) {
        if (TextUtils.isEmpty(str.trim())) {
            _toast.value = "Can not empty!"
        } else {
            val chatBean = ChatItem(
                id = 0,
                name = "Me",
                time = getTime(),
                textMsg = str,
                sender = ChatSender.OWNER
            )
            val json = Gson().toJson(chatBean)
            webSocket.send(json)
            addChat(chatBean)
        }
    }

    fun sendImg(paths: List<String>) {
        val imgBytes = compressToByteArray(paths)
        val chatImgList = imgBytes.mapIndexed { index, bytes ->
            ChatImg(index, bytes)
        }
        val chatBean = ChatItem(
            id = 0,
            name = "Me",
            time = getTime(),
            imgListMsg = chatImgList,
            sender = ChatSender.OWNER
        )
        val json = Gson().toJson(chatBean)
        webSocket.send(json)
        addChat(chatBean)
    }

    //圖片壓縮
    private fun compressToByteArray(paths: List<String>): List<ByteArray> {
        return paths.map {
            val bitmap = File(it).getBitmap(BitmapConfig())
            val byteArray = bitmap.compressToByteArray(BitmapCompress())
            println("$TAG byteArray:size: ${byteArray.size}")
            byteArray
        }
    }

    fun sendVideo(paths: List<String>) {
        paths.forEach {
            val videoBytes = it.toByteArray()
            val chatVideoList = videoBytes.mapIndexed { index, bytes -> ChatVideo(index, bytes) }
            val chatItem = ChatItem(
                id = 0,
                name = "Me",
                time = getTime(),
                videoListMsg = chatVideoList,
                sender = ChatSender.OWNER
            )
            val json = Gson().toJson(chatItem)
            webSocket.send(json)
            addChat(chatItem)
        }
    }

    fun sendRecording(result: RecorderResult) {
        val recordingBytes = result.file!!.readBytes()
        val chatAudio = ChatAudio(id = 0, byteAttay = recordingBytes, time = result.lengthTime, countDownTimer = result.lengthTime)
        val chatItem = ChatItem(
            id = 0,
            name = "Me",
            time = getTime(),
            audioMsg = chatAudio,
            sender = ChatSender.OWNER
        )
        val json = Gson().toJson(chatItem)
        webSocket.send(json)
        addChat(chatItem)
    }
    //
    //


    //--------------------------------------------------------------------------------------
    //view state
    private val _isInputEmpty = mutableLiveData<Boolean>(true)

    val isInputEmpty = _isInputEmpty.asLiveData()

    private val _inputSrc = mutableLiveData<Int>(R.drawable.ic_baseline_send_24_gray)

    val inputSrc = _inputSrc.asLiveData()

    fun setInput(state: Boolean) {
        _isInputEmpty.value = state
        _inputSrc.value = if (state) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    //--------------------------------------------------------------------------------------
    private val _addItems: MutableLiveData<List<AddItem>> by lazy {
        MutableLiveData<List<AddItem>>(
            listOf<AddItem>(
                AddItem(R.string.camera, R.drawable.ic_baseline_photo_camera_24_gray),
                AddItem(R.string.album, R.drawable.ic_baseline_photo_24_gray),
                AddItem(R.string.audio, R.drawable.ic_baseline_mic_none_24_gray),
                AddItem(R.string.video, R.drawable.ic_baseline_videocam_24_gray),
                AddItem(R.string.movie, R.drawable.ic_baseline_local_movies_24_gray)
            )
        )
    }

    //camera
    private val _camera = mutableLiveData<Boolean>(false)

    val camera = _camera.asLiveData()

    fun switchToCamera() {
        _camera.postValue(true)
        viewModelScope.launch {
            delay(500)
            _camera.postValue(false)
        }
    }

    //album
    private val _album = mutableLiveData<Boolean>(false)

    val album = _album.asLiveData()

    fun switchToAlbum() {
        _album.postValue(true)
        viewModelScope.launch {
            delay(500)
            _album.postValue(false)
        }
    }

    //audio
    private val _audio = mutableLiveData<Boolean>(false)

    val audio = _audio.asLiveData()

    fun switchToAudio() {
        _audio.postValue(true)
        viewModelScope.launch {
            delay(500)
            _audio.postValue(false)
        }
    }

    //recording
    private val _recording = mutableLiveData<Boolean>(false)

    val recording = _recording.asLiveData()

    fun switchToRecording() {
        _recording.postValue(true)
        viewModelScope.launch {
            delay(500)
            _recording.postValue(false)
        }
    }

    //photo graphy
    private val _photography = mutableLiveData<Boolean>(false)

    val photography = _photography.asLiveData()

    fun switchToPhotography() {
        _photography.postValue(true)
        viewModelScope.launch {
            delay(500)
            _photography.postValue(false)
        }
    }

    //video
    private val _video = mutableLiveData<Boolean>(false)

    val video = _video.asLiveData()

    //for open video list
    fun switchToVideo() {
        _video.postValue(true)
        viewModelScope.launch {
            delay(500)
            _video.postValue(false)
        }
    }

    //--------------------------------------------------------------------------------------
    //recording
    val recordingState = recordingRepo.stateRelay.toLiveData(BackpressureStrategy.LATEST)

    val recordingResultRelay: PublishRelay<Pair<Boolean, RecorderResult?>> = recordingRepo.resultRelay

    val recordingLengthTimeRelay = recordingRepo.lengthTimeRelay.toLiveData(BackpressureStrategy.LATEST)

    fun startRecording(setting: RecorderSetting) {
        recordingRepo.prepare(setting = setting)
        recordingRepo.start()
    }

    fun stopRecording() = recordingRepo.stop()

    val recordingArc = recordingState.combine(recordingLengthTimeRelay).map {
        it.first?.let {
            if (it) R.drawable.ic_baseline_radio_button_unchecked_24_red else R.drawable.ic_baseline_radio_button_unchecked_24_gray

        } ?: R.drawable.ic_baseline_radio_button_unchecked_24_gray
    }

    val recordingPlayList = mutableListOf<ChatItem>()//存取列表中正在播放的音訊

    private var mediaPlayer: MediaPlayer? = null

    private var recordingCountDownTimer: CountDownTimer? = null

    fun startPlayer(chatItem: ChatItem) {
        chatItem.audioMsg?.let {
            recordingCountDownTimer = object : CountDownTimer((it.countDownTimer * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    it.countDownTimer = (millisUntilFinished / 1000).toInt()
                }

                override fun onFinish() {
                    it.countDownTimer = it.time
                }
            }.start()
        }
        //
        //
        //
        val fileName = "Audio_${System.nanoTime()}.3gp"
        val file = context.externalCacheDir?.createFile(fileName)
        file?.writeBytes(chatItem.audioMsg?.byteAttay!!)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file?.absolutePath)
            prepare()
            start()
        }
    }

    fun stopPlayer() {
        recordingCountDownTimer?.onFinish()
        //
        //
        //
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.reset()
                it.stop()
                it.release()
                mediaPlayer = null
            }
        }
    }


    //
    //
    //
    //
    //
    //
    //
    //


    //
    //
    //

    private val _socketStateFlow = mutableStateFlow<SocketState>(SocketState.Idle)

    val socketStateFlow = _socketStateFlow.asStateFlow()

    val socketViewEvent = Channel<SocketViewEvent>(Channel.UNLIMITED)

    val webSocketUrlRelay = BehaviorRelay.create<String>()
    //


    //


    //
    //
    //


    val addItems: LiveData<List<AddItem>> = _addItems

    private fun initViewEvent() = viewModelScope.launch(Dispatchers.IO) {
        socketViewEvent
            .consumeAsFlow()
            .collect {
                when (it) {

                    is SocketViewEvent.SendImg -> {
                        val imgBytes = it.uris.map {
                            val bitmap = it.getBitmap(context.contentResolver, BitmapConfig())
                            val byteArray = bitmap?.compressToByteArray(BitmapCompress())
                            println("$TAG byteArray:size: ${byteArray!!.size}")
                            byteArray
                        }
                        val chatImgList = imgBytes.mapIndexed { index, bytes -> ChatImg(index, bytes!!) }
                        val chatBean = ChatItem(
                            id = 0, name = "Me", time = getTime(), sender = ChatSender.OWNER, imgListMsg = chatImgList
                        )
                        val json = Gson().toJson(chatBean)
                        webSocket.send(json)
                        _socketStateFlow.value = SocketState.ShowChat(chatItem = chatBean)
                    }

                    is SocketViewEvent.SendRecorder -> {
//                        val chatBean = ChatItem(id = 0, name = "Me", sender = ChatSender.OWNER, time = getTime(), audioMsg = it.file.readBytes())
//                        val json = Gson().toJson(chatBean)
//                        println("$TAG json:$json")
//                        webSocket.send(json)
//                        _socketStateFlow.value = SocketState.ShowChat(chatItem = chatBean)
                    }
                }
            }
    }


    //
    //
    //camera
    private val mCameraLiveData: MutableLiveData<IntentResult> by lazy { MutableLiveData<IntentResult>() }
    val cameraLiveData: LiveData<IntentResult> = mCameraLiveData

    //album
    private val mAlbumLiveData: MutableLiveData<IntentResult> by lazy { MutableLiveData<IntentResult>() }
    val albumLiveData: LiveData<IntentResult> = mAlbumLiveData


    //camera
    fun createCameraBuilder(doStart: (builder: IntentBuilder) -> Unit) {
        repo.createCameraBuilder(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { doStart(it) }
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun onCameraResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<IntentResult> {
        val result = IntentResult.IntentResultDefault(requestCode, resultCode, data)
        return repo.onCameraResult(result)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //crop
    fun createCropBuilder(uri: Uri, doStart: (builder: IntentBuilder) -> Unit) {
        repo.createCropBuilder(uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { doStart(it) }
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun onCropResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<IntentResult> {
        val result = IntentResult.IntentResultDefault(requestCode, resultCode, data)
        return repo.onCropResult(result)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


    fun cameraCropResultEvent(intentResult: IntentResult) = mCameraLiveData.postValue(intentResult)

    fun albumResultEvent(result: IntentResult) = mAlbumLiveData.postValue(result)


    //----------------------------------------------


    //----------------------------------------------


    //----------------------------------------------


//    fun startPlayer(byteArray: ByteArray) {
//        val fileName = "Audio_${System.nanoTime()}.3gp"
//        val file = context.externalCacheDir?.createFile(fileName)
//        file?.writeBytes(byteArray)
//        mediaPlayer = MediaPlayer().apply {
//            setDataSource(file?.absolutePath)
//            prepare()
//        }
//        mediaPlayer?.let {
//            if (!it.isPlaying) {
//                it.start()
//                mMediaPlayerState.postValue(true)
//            }
//        }
//
//    }
//
//    fun stopPlayer() {
//        mediaPlayer?.let {
//            if (it.isPlaying) {
//                it.reset()
//                it.stop()
//                it.release()
//                mediaPlayer = null
//            }
//            mMediaPlayerState.postValue(true)
//        }
//    }

}