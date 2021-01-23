package edu.yujie.socketex.vm

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.R
import edu.yujie.socketex.SocketState
import edu.yujie.socketex.SocketViewEvent
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.*
import edu.yujie.socketex.ext.BitmapCompress
import edu.yujie.socketex.ext.BitmapConfig
import edu.yujie.socketex.ext.compressToByteArray
import edu.yujie.socketex.ext.getBitmap
import edu.yujie.socketex.inter.IMediaRepo2
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
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class ChatRoomViewModel(application: Application, private val repo2: IMediaRepo2) : BaseAndroidViewModel(application), KoinComponent {

    val chatSocketRepository = ChatSocketRepository()

    private val infoList = mutableListOf<String>()

    private val _infoList = mutableLiveData(infoList)

    private val chatList = mutableListOf<ChatItem>()

    private val _chatList = mutableLiveData(chatList)

    val chatListLiveData = _chatList.asLiveData()

    private val _toast = mutableLiveData<String>("")

    val toast = _toast.asLiveData()

    lateinit var webSocket: WebSocket

    val sendFinish = mutableLiveData<ChatItem?>()

    fun execute(): Observable<WebSocket> = chatSocketRepository.executeMockServer()
        .flatMap { chatSocketRepository.executeClientSocket(it) }
        .map {
            webSocket = it
            it
        }

    //    fun receiveInfoList(): LiveData<List<String>> {
    fun receiveInfoList(): Observable<List<String>> {
        return execute()
            .flatMap {
                println("ChatRoomViewModel:receiveInfo2")
                chatSocketRepository.receiveInfo()
            }
            .map { addInfo(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//            .toLiveData(BackpressureStrategy.LATEST)
    }

    private fun addInfo(str: String): List<String> = infoList.also {
        it.add(str)
        _infoList.postValue(it)
    }

    fun receiveChatList(): LiveData<List<ChatItem>> = execute()
        .flatMap { chatSocketRepository.receiveChat() }
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
        .map { addChat(it) }
        .toLiveData(BackpressureStrategy.LATEST)

    fun addChat(chatItem: ChatItem): List<ChatItem> {
        chatList.also {
            it.add(chatItem)
            _chatList.value = it
        }
        return chatListLiveData.value as List<ChatItem>
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
            sendFinish.value = chatBean
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
    //
    private val okHttpUtil by inject<OkHttpUtil>()

    private val _socketStateFlow = mutableStateFlow<SocketState>(SocketState.Idle)

    val socketStateFlow = _socketStateFlow.asStateFlow()

    val socketViewEvent = Channel<SocketViewEvent>(Channel.UNLIMITED)

    val webSocketUrlRelay = BehaviorRelay.create<String>()
    //

    private val _isInputEmpty: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(true) }

    val isInputEmpty = _isInputEmpty.asLiveData()
    //


    fun setInput(state: Boolean) = _isInputEmpty.postValue(state)

    //


    fun sendImg(paths: List<String>) {
        val imgBytes = compressToByteArray(paths)
        val chatImgList = imgBytes.mapIndexed { index, bytes -> ChatImg(index, bytes) }
        val chatBean = ChatItem(
            id = 0, name = "Me", time = getTime(), sender = ChatSender.OWNER, imgListMsg = chatImgList
        )
        val json = Gson().toJson(chatBean)
        webSocket.send(json)
        sendFinish.value = chatBean
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
    //
    //
    //


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
                        val chatBean = ChatItem(id = 0, name = "Me", sender = ChatSender.OWNER, time = getTime(), audioMsg = it.file.readBytes())
                        val json = Gson().toJson(chatBean)
                        println("$TAG json:$json")
                        webSocket.send(json)
                        _socketStateFlow.value = SocketState.ShowChat(chatItem = chatBean)
                    }
                }
            }
    }

    //

    val toastLiveData: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }


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
        repo2.createCameraBuilder(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { doStart(it) }
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun onCameraResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<IntentResult> {
        val result = IntentResult.IntentResultDefault(requestCode, resultCode, data)
        return repo2.onCameraResult(result)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    //crop
    fun createCropBuilder(uri: Uri, doStart: (builder: IntentBuilder) -> Unit) {
        repo2.createCropBuilder(uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { doStart(it) }
            .addTo(compositeDisposable = compositeDisposable)
    }

    fun onCropResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<IntentResult> {
        val result = IntentResult.IntentResultDefault(requestCode, resultCode, data)
        return repo2.onCropResult(result)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


    fun cameraCropResultEvent(intentResult: IntentResult) = mCameraLiveData.postValue(intentResult)

    fun albumResultEvent(result: IntentResult) = mAlbumLiveData.postValue(result)

    //recorder
    val recordingStateRelay: BehaviorRelay<Boolean>
        get() = repo2.recordingStateRelay

    val enoughRecordingTimeRelay: BehaviorRelay<Boolean>
        get() = repo2.enoughRecordingTimeRelay

    fun startRecording(doStart: (Long) -> Unit) {
        repo2.prepareRecording(context).subscribe {
            repo2.startRecording()
                .subscribe { doStart(it) }
        }.addTo(compositeDisposable = compositeDisposable)
    }

    fun stopRecording() {
        val disposable = repo2.stopRecording().subscribe()
        compositeDisposable.add(disposable)
    }

    //----------------------------------------------
    //album
    private val _album: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    val album: LiveData<Boolean> = _album

    //for open album list
    fun switchToAlbum() {
        _album.postValue(true)
        viewModelScope.launch {
            delay(500)
            _album.postValue(false)
        }
    }

    //----------------------------------------------
    //mic
    private val _mic: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    val mic: LiveData<Boolean> = _mic

    //for open recorder
    fun switchToAudio() {
        _mic.postValue(true)
        viewModelScope.launch {
            delay(500)
            _mic.postValue(false)
        }
    }

    //----------------------------------------------
    //video
    private val _video: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(false) }

    val video = _video.asLiveData()

    //for open video list
    fun switchToVideo() {
        _video.postValue(true)
        viewModelScope.launch {
            delay(500)
            _video.postValue(false)
        }
    }

    //----------------------------------------------


//    //recorder
//    fun openMic() = mMicStateLiveData.postValue(true)


    var recorderStateRelay = BehaviorRelay.create<Boolean>()

    val errorRelay = BehaviorRelay.create<Boolean>()


//    fun startRecorder() {
////        val fileName = "Audio_${System.nanoTime()}.3gp"
////        val file = FileExt.createFile(context.externalCacheDir, fileName)
////        recorder.buildRecorder()
////            .subscribeOn(Schedulers.io())
////            .observeOn(AndroidSchedulers.mainThread())
////            .doOnComplete {
////                recorder.startRecorder()
////            }
////        ////////////////////////////////////////////////
////
////        Observable.just(1, 2, 3, 4)
////            .subscribeOn(Schedulers.io())
////            .observeOn(AndroidSchedulers.mainThread())
////            .doOnError {
////
////            }
//
//    }

    //
    //


    //audio recorder
    private var recorderFile: File? = null
    private var mediaRecorder: MediaRecorder? = null

    private val mRecorderStateFileLiveData: MutableLiveData<Pair<Boolean, File?>> by lazy { MutableLiveData<Pair<Boolean, File?>>(false to null) }
    val recorderStateFileLiveData: LiveData<Pair<Boolean, File?>> = mRecorderStateFileLiveData

    private var mediaPlayer: MediaPlayer? = null

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


    fun startPlayer(byteArray: ByteArray) {
        val fileName = "Audio_${System.nanoTime()}.3gp"
        val file = context.externalCacheDir?.createFile(fileName)
        file?.writeBytes(byteArray)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(file?.absolutePath)
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

    private fun convertBeanJson(text: String): String {
        val chatBean = Gson().fromJson(text, ChatItem::class.java)
        val msg = if (chatBean.textMsg != null) "${chatBean.textMsg} - From Server" else null
        val imgBytes = chatBean.imgListMsg
        val recorderBytes = chatBean.audioMsg
        val chatBeanOther = ChatItem(id = -1, name = "Ohter", textMsg = msg, sender = ChatSender.OTHER, time = getTime(), imgListMsg = imgBytes, audioMsg = recorderBytes)
        val json = Gson().toJson(chatBeanOther)
        return json
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

//    //album
//    fun createAlbumBuilder(doStart: (builder: IntentBuilder) -> Unit) {
//        repo2.createAlbumBuilder()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                doStart(it)
//            }.addTo(compositeDisposable = compositeDisposable)
//    }
//
//    fun onAlbumResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<IntentResult> {
//        val result = IntentResult.IntentResultDefault(requestCode, resultCode, data)
//        return repo2.onAlbumResult(result)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
}