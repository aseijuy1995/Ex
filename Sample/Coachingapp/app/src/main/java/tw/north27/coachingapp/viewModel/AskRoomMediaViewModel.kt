package tw.north27.coachingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.utilmodule.base.BaseViewModel
import com.yujie.utilmodule.ext.asLiveData
import com.yujie.utilmodule.media.model.Media
import com.yujie.utilmodule.media.model.MediaSetting
import com.yujie.utilmodule.media.model.MimeType
import com.yujie.utilmodule.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tw.north27.coachingapp.repository.IMediaRepository

class AskRoomMediaViewModel(
    private val repo: IMediaRepository
) : BaseViewModel() {

    private val _mediaImageListState = MutableLiveData<ViewState<List<Media>>>()

    val mediaImageListState = _mediaImageListState.asLiveData()

    val albumSetting = MediaSetting(
        mimeType = MimeType.IMAGE,
//        isMultipleChoice = true,
//        multipleChoiceMaxCount = 6,
    )

    fun fetchMediaImage(setting: MediaSetting) = viewModelScope.launch(Dispatchers.IO) {
        _mediaImageListState.postValue(ViewState.load())
        repo.fetchMediaImage(setting).collect {
            if (it.isEmpty()) {
                _mediaImageListState.postValue(ViewState.empty())
            } else {
                _mediaImageListState.postValue(ViewState.data(it))
            }

        }
    }

    val audioSetting = MediaSetting(
        mimeType = MimeType.AUDIO,
//        isMultipleChoice = true,
//        multipleChoiceMaxCount = 3,
        minSec = 0,
        maxSec = 60
    )

    val videoSetting = MediaSetting(
        mimeType = MimeType.VIDEO,
//        isMultipleChoice = true,
//        multipleChoiceMaxCount = 3,
        minSec = 0,
        maxSec = 180
    )

//    /**
//     * 全部媒體項目
//     * */
//    private val _mediaList = MutableLiveData<MutableList<Media>?>()
//
//    val mediaList = _mediaList.asLiveData()
//
//    val choiceMediaList = mediaList.map { it?.filter { it.isChoice } }
//
//    fun setMediaList(mediaList: MutableList<Media>?) {
//        _mediaList.value = mediaList
//    }
//
//    fun setChoiceOfMedia(media: Media) {
//        _mediaList.value = mediaList.value?.also {
//            it.find { media.id == it.id }?.isChoice = media.isChoice
//        }
//    }

}