package tw.north27.coachingapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yujie.core_lib.base.BaseViewModel
import com.yujie.core_lib.ext.asLiveData
import com.yujie.core_lib.model.MediaSetting
import com.yujie.core_lib.model.MimeType
import com.yujie.core_lib.util.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tw.north27.coachingapp.model.media.MediaConfig
import tw.north27.coachingapp.model.media.MediaData
import tw.north27.coachingapp.repository.IMediaRepository

class AskRoomMediaViewModel(
    private val repo: IMediaRepository
) : BaseViewModel() {

    private val _mediaImageListState = MutableLiveData<ViewState<List<MediaData>>>()

    val mediaImageListState = _mediaImageListState.asLiveData()

    val albumConfig = MediaConfig(
        mimeType = MimeType.IMAGE,
        isMultipleChoice = true,
        multipleChoiceMaxCount = 6,
    )

    fun fetchMediaImage() = viewModelScope.launch(Dispatchers.IO) {
        _mediaImageListState.postValue(ViewState.load())
        repo.fetchMediaImage(
            MediaSetting(mimeType = MimeType.IMAGE)
        ).collect {
            if (it.isEmpty()) {
                _mediaImageListState.postValue(ViewState.empty())
            } else {
                val mediaDataList = mutableListOf<MediaData>()
                it.forEach {
                    mediaDataList.add(
                        MediaData(
                            id = it.id,
                            mimeType = it.mimeType,
                            defaultSortOrder = it.defaultSortOrder,
                            path = it.path,
                            displayName = it.displayName,
                            size = it.size,
                            height = it.height,
                            width = it.width,
                        )
                    )
                }
                _mediaImageListState.postValue(ViewState.data(mediaDataList))
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