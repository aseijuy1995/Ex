package tw.north27.coachingapp.chat

import androidx.lifecycle.MutableLiveData
import tw.north27.coachingapp.R
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.ext.asLiveData

class ChatRoomAddViewModel : BaseViewModel() {

    private val _chatRoomAddList: MutableLiveData<List<ChatRoomAdd>> by lazy {
        MutableLiveData<List<ChatRoomAdd>>(
            listOf<ChatRoomAdd>(
                ChatRoomAdd(R.drawable.ic_baseline_photo_camera_24_red, R.string.camera),
                ChatRoomAdd(R.drawable.ic_baseline_photo_24_orange, R.string.album),
                ChatRoomAdd(R.drawable.ic_baseline_mic_none_24_yellow, R.string.recording),
                ChatRoomAdd(R.drawable.ic_baseline_queue_music_24_green, R.string.audio),
                ChatRoomAdd(R.drawable.ic_baseline_videocam_24_blue, R.string.video),
                ChatRoomAdd(R.drawable.ic_baseline_local_movies_24_blue, R.string.movie),
                ChatRoomAdd(R.drawable.ic_baseline_phone_24_purple, R.string.phone)
            )
        )
    }

    val chatRoomAddList = _chatRoomAddList.asLiveData()

    private val _request = MutableLiveData<Pair<ChatRoomAddFeature, Boolean>>()

    val request = _request.asLiveData()

    fun request(type: ChatRoomAddFeature, isRequest: Boolean) {
        _request.value = (type to isRequest)
    }

    sealed class ChatRoomAddFeature {
        object CAMERA : ChatRoomAddFeature()
        object PHOTO : ChatRoomAddFeature()
        object MIC : ChatRoomAddFeature()
        object AUDIO : ChatRoomAddFeature()
        object VIDEO : ChatRoomAddFeature()
        object MOVIE : ChatRoomAddFeature()
    }

}