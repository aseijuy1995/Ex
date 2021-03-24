package tw.north27.coachingapp.media

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.core.Observable
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.chat.IMediaRepository
import tw.north27.coachingapp.chat.Media
import tw.north27.coachingapp.chat.MediaAlbum
import tw.north27.coachingapp.chat.MediaSetting
import tw.north27.coachingapp.ext.asLiveData

class MediaViewModel(private val repo: IMediaRepository) : BaseViewModel() {

    fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>> {
        return repo.getMediaAudio(setting)
    }

    fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>> {
        return repo.getMediaImages(setting)
    }

    fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>> {
        return repo.getMediaVideo(setting)
    }

    private val _mediaList = MutableLiveData<MutableList<Media>?>()

    /**
     * 全部媒體項目
     * */
    val mediaList = _mediaList.asLiveData()

    /**
     * 被選取的媒體
     * */
    val selectMediaList = mediaList.value?.filter { it.isChoice }

    fun setMediaList(mediaList: MutableList<Media>?) {
        _mediaList.value = mediaList
    }

    fun setMedia(media: Media) {
        _mediaList.value = mediaList.value?.also {
            it.find { media.id == it.id }?.isChoice = media.isChoice
        }
    }

}