package tw.north27.coachingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.http.Results
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.AskInfos
import tw.north27.coachingapp.model.UserInfo
import tw.north27.coachingapp.repository.IActionRepository
import tw.north27.coachingapp.repository.IUserRepository

class AskRoomViewModel(
    val actionRepo: IActionRepository,
    val userRepo: IUserRepository
) : BaseViewModel() {

    private val _userInfoState = MutableLiveData<ViewState<UserInfo>>(ViewState.Initial)

    val userInfoState = _userInfoState.asLiveData()

    fun fetchUser(account: String) = viewModelScope.launch(Dispatchers.IO) {
        _userInfoState.postValue(ViewState.load())
        val results = userRepo.fetchUser(account = account)
        when (results) {
            is Results.Successful -> {
                val userInfo = results.data
                _userInfoState.postValue(ViewState.data(userInfo))
            }
            is Results.ClientErrors -> {
                _userInfoState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _userInfoState.postValue(ViewState.network(results.e))
            }
        }
    }

    private val _askRoomState = MutableLiveData<ViewState<AskInfos>>(ViewState.Initial)

    val askRoomState = _askRoomState.asLiveData()

    fun fetchAskRoomList(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        _askRoomState.postValue(ViewState.load())
        val results = actionRepo.fetchAskRoomList(id)
        when (results) {
            is Results.Successful -> {
                val askRoom = results.data
                if (askRoom.askInfoList.isNullOrEmpty())
                    _askRoomState.postValue(ViewState.empty())
                else
                    _askRoomState.postValue(ViewState.data(askRoom))
            }
            is Results.ClientErrors -> {
                _askRoomState.postValue(ViewState.error(results.e))
            }
            is Results.NetWorkError -> {
                _askRoomState.postValue(ViewState.network(results.e))
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