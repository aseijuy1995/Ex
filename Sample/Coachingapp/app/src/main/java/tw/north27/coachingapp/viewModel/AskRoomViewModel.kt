package tw.north27.coachingapp.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseAndroidViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.http.Results
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.request.AskRoomInfoRequest
import tw.north27.coachingapp.repository.IAskRoomRepository
import tw.north27.coachingapp.repository.IUserRepository

class AskRoomViewModel(
    application: Application,
    val askRoomRepo: IAskRoomRepository,
    val userRepo: IUserRepository
) : BaseAndroidViewModel(application) {

    private val _askInfoListState = MutableLiveData<ViewState<List<AskRoomInfo>>>(ViewState.Initial)

    val askInfoListState = _askInfoListState.asLiveData()

    fun fetchAskRoomInfoList(roomId: Long) = viewModelScope.launch(Dispatchers.IO) {
        _askInfoListState.postValue(ViewState.load())
        val clientId = cxt.userPref.getId().first()
        val results = askRoomRepo.fetchAskRoomInfoList(
            AskRoomInfoRequest(
                roomId = roomId,
                clientId = clientId,
                index = 0,
                num = 0
            )
        )
        when (results) {
            is Results.Successful<List<AskRoomInfo>> -> {
                val askInfoList = results.data
                if (askInfoList.isNullOrEmpty())
                    _askInfoListState.postValue(ViewState.empty())
                else
                    _askInfoListState.postValue(ViewState.data(askInfoList))
            }
            is Results.ClientErrors -> {
                _askInfoListState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askInfoListState.postValue(ViewState.network(results.e))
            }
        }
    }


//
//    val message = chatRepo.message
//
//    /**
//     * 聊天室列表置底
//     * */
//    private val _roomScrollToBottom = MutableLiveData<Boolean>(false)
//
//    val roomScrollToBottom = _roomScrollToBottom.asLiveData()
//
//    fun roomScrollToBottom(isScrollToBottom: Boolean) {
//        _roomScrollToBottom.value = isScrollToBottom
//    }
//
//    fun addChat(chat: ChatInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val list: MutableList<ChatInfo> = if (chatListState.value is ViewState.Data) (chatListState.value as ViewState.Data).data.toMutableList() else mutableListOf()
//            list.add(chat)
//            _chatListState.value = ViewState.data(list)
//        }
//    }
//
//    fun send(chat: ChatInfo) {
//        viewModelScope.launch(Dispatchers.IO) {
//            /**
//             * FIXME 需增加連線中斷處理機制
//             * */
//            chatRepo.webSocket?.let {
//                addChat(chat)
//                chatRepo.send(it, chat)
//            }
//        }
//    }
//
//    private val _inputEmpty = MutableLiveData<Boolean>(true)
//
//    val inputEmpty = _inputEmpty.asLiveData()
//
//    val sendRes = _inputEmpty.map {
//        if (it) R.drawable.ic_baseline_send_24_gray else R.drawable.ic_baseline_send_24_blue
//    }
//
//    fun inputEmpty(isEmpty: Boolean) {
//        _inputEmpty.value = isEmpty
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
////    fun audioDecodeToPcm(filePath: String):String {
////        val pcmPath = "${context.externalCacheDir}/Champagne_Edition_2.pcm"
////        viewModelScope.launch(Dispatchers.IO) {
////            mediaRepo.audioDecodeToPcm(filePath, pcmPath)
////        }
////        return pcmPath
////    }
//
//    val recordingTime = mediaRepo.recordingTime
//
//    fun startRecording(setting: RecorderSetting) {
//        mediaRepo.prepareRecording(setting)
//        mediaRepo.startRecording()
//    }
//
//    fun stopRecording() {
//        mediaRepo.stopRecording()
//    }
//
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    val bitmapOption = BitmapOption(
//        isOptions = true,
//        reqWidth = 400,
//        reqHeight = 800
//    )
//
//    val bitmapCompress = BitmapCompress(
//        isCompress = true,
//        format = Bitmap.CompressFormat.PNG,
//        quality = 80
//    )
//
//    /**
//     * 壓縮圖片
//     * */
//    fun compressedImg(mediaList: List<Media>): List<ByteArray> {
////        val inputStream = File(it.data).getBitmap(bitmapOption).toInputStream(bitmapCompress)
//        val imgByteArray = mediaList.map { File(it.data).getBitmap(bitmapOption).toByteArray(bitmapCompress) }
//        return imgByteArray
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private val _toast = MutableLiveData<Pair<ToastType, String>>()
//
//    val toast = _toast.asLiveData()
//
//    enum class ToastType {
//        LOAD_CHAT_MESSAGE_LIST
//    }
//
////    fun addChatMessage(chat: ChatInfo) {
////        _chatMessageList
////    }
}