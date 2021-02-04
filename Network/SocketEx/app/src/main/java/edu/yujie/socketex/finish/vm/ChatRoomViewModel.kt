package edu.yujie.socketex.finish.vm

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
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
import edu.yujie.socketex.bean.*
import edu.yujie.socketex.ext.BitmapCompress
import edu.yujie.socketex.ext.BitmapConfig
import edu.yujie.socketex.ext.compressToByteArray
import edu.yujie.socketex.ext.getBitmap
import edu.yujie.socketex.finish.base.viewModel.BaseAndroidViewModel
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
    private val infoes = mutableListOf<String>()

    private val _infoList = mutableLiveData(infoes)

    val infoList = _infoList.asLiveData()

    private val chatItems = mutableListOf<ChatItem>()

    private val _chatItemList = mutableLiveData(chatItems)

    val chatItemList = _chatItemList.asLiveData()

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

    private fun addInfo(str: String) = infoes.also {
        it.add(str)
        _infoList.value = it
    }

    fun receiveChatList() = chatSocketRepository.receiveChat()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { addChatItem(it) }
        .addTo(compositeDisposable = compositeDisposable)

    fun addChatItem(chatItem: ChatItem) = chatItems.also {
        it.add(chatItem)
        _chatItemList.value = it
    }

    fun sendText(str: String) {
        val chatItem = ChatItem(
            id = ++count,
            name = "Owner",
            time = getTime(),
            textMsg = str,
            sender = ChatSender.OWNER
        )
        val json = Gson().toJson(chatItem)
        addChatItem(chatItem)
        webSocket.send(json)
    }

    fun sendImg(paths: List<String>) {
        val imgBytes = compressToByteArray(paths)
        val chatImgList = imgBytes.mapIndexed { index, bytes ->
            ChatImg(index, bytes)
        }
        val chatBean = ChatItem(
//            id = 0,
            id = ++count,
            name = "Owner",
            time = getTime(),
            imgListMsg = chatImgList,
            sender = ChatSender.OWNER
        )
        val json = Gson().toJson(chatBean)
        webSocket.send(json)
        addChatItem(chatBean)
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
//                id = 0,
                id = ++count,
                name = "Owner",
                time = getTime(),
                videoListMsg = chatVideoList,
                sender = ChatSender.OWNER
            )
            val json = Gson().toJson(chatItem)
            webSocket.send(json)
            addChatItem(chatItem)
        }
    }

    fun sendRecording(result: RecorderResult) {
        val recordingBytes = result.file!!.readBytes()
        val chatAudio = ChatAudio(id = 0, byteAttay = recordingBytes, time = result.lengthTime, countDownTimer = result.lengthTime)
        val chatItem = ChatItem(
//            id = 0,
            id = ++count,
            name = "Owner",
            time = getTime(),
            audioMsg = chatAudio,
            sender = ChatSender.OWNER
        )
        val json = Gson().toJson(chatItem)
        webSocket.send(json)
        addChatItem(chatItem)
    }
    //
    //

    //--------------------------------------------------------------------------------------
    //view state
    private val _isInputEmpty = mutableLiveData<Boolean>(true)

    val isInputEmpty = _isInputEmpty.asLiveData()

    val inputSrc = _isInputEmpty.map {
        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
    }

    fun setIsInputEmpty(state: Boolean) {
        _isInputEmpty.value = state
    }


    //--------------------------------------------------------------------------------------
    private val _addItems: MutableLiveData<List<AddItem>> by lazy {
        MutableLiveData<List<AddItem>>(
            listOf<AddItem>(
                AddItem(R.string.camera, R.drawable.ic_baseline_photo_camera_24_gray),
                AddItem(R.string.album, R.drawable.ic_baseline_photo_24_gray),
                AddItem(R.string.recording, R.drawable.ic_baseline_mic_none_24_gray),
                AddItem(R.string.audio, R.drawable.ic_baseline_audiotrack_24_gray),
                AddItem(R.string.video, R.drawable.ic_baseline_videocam_24_gray),
                AddItem(R.string.movie, R.drawable.ic_baseline_local_movies_24_gray)
            )
        )
    }

    val addItems = _addItems.asLiveData()

    //camera
    private val _camera = mutableLiveData<Boolean>(false)

    val camera = _camera.asLiveData()

    fun switchToCamera() {
        _camera.postValue(true)
        viewModelScope.launch {
            delay(500L)
            _camera.postValue(false)
        }
    }

    //album
    private val _album = mutableLiveData<Boolean>(false)

    val album = _album.asLiveData()

    fun switchToAlbum() {
        _album.postValue(true)
        viewModelScope.launch {
            delay(500L)
            _album.postValue(false)
        }
    }

    //recording
    private val _recording = mutableLiveData<Boolean>(false)

    val recording = _recording.asLiveData()

    fun switchToRecording() {
        _recording.postValue(true)
        viewModelScope.launch {
            delay(500L)
            _recording.postValue(false)
        }
    }

    //audio
    private val _audio = mutableLiveData<Boolean>(false)

    val audio = _audio.asLiveData()

    fun switchToAudio() {
        _audio.postValue(true)
        viewModelScope.launch {
            delay(500L)
            _audio.postValue(false)
        }
    }

    //photography
    private val _photography = mutableLiveData<Boolean>(false)

    val photography = _photography.asLiveData()

    fun switchToPhotography() {
        _photography.postValue(true)
        viewModelScope.launch {
            delay(500L)
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
            delay(500L)
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

    private var mediaPlayer: MediaPlayer? = null

    private var recordingCountDownTimer: CountDownTimer? = null

    fun startRecordingPlayer(chatItem: ChatItem) {
        chatItem.audioMsg?.let {
            startRecordingTimer(chatItem)
            //
            //
            //
            val fileName = "Audio_${System.nanoTime()}.3gp"
            val file = context.externalCacheDir?.createFile(fileName)
            file?.writeBytes(it.byteAttay)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file?.absolutePath)
                prepare()
                start()
            }
        }
    }

//    private val recordiingList

    private fun startRecordingTimer(chatItem: ChatItem) {
        chatItem.audioMsg?.let {
            recordingCountDownTimer = object : CountDownTimer((it.countDownTimer * 1000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    it.countDownTimer = (it.time - (millisUntilFinished / 1000)).toInt()
                    recordingTimeChange(chatItem)
                }

                override fun onFinish() {
                    it.countDownTimer = it.time
                    recordingTimeChange(chatItem)
                }
            }.start()
        }
    }

    fun recordingTimeChange(chatItem: ChatItem) {
        println("$TAG recordingTimeChange")
        val chatList = _chatItemList.value
        chatList?.mapIndexed { index, chatItem2 ->
            if (chatItem2.id == chatItem.id) {
                println("$TAG chatItem2.id:${chatItem2.audioMsg?.countDownTimer}, ${chatItem2.audioMsg?.time}")
                chatList[index] = chatItem
            }
        }
        _chatItemList.postValue(chatList)
    }

    fun stopRecordingPlayer() {
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
//                            id = 0,
                            id = ++count,
                            name = "Owner",
                            time = getTime(),
                            sender = ChatSender.OWNER,
                            imgListMsg = chatImgList
                        )
                        val json = Gson().toJson(chatBean)
                        webSocket.send(json)
                        _socketStateFlow.value = SocketState.ShowChat(chatItem = chatBean)
                    }

                    is SocketViewEvent.SendRecorder -> {
//                        val chatBean = ChatItem(id = 0, name = "Owner", sender = ChatSender.OWNER, time = getTime(), audioMsg = it.file.readBytes())
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