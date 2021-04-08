package tw.north27.coachingapp.media

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import io.reactivex.rxjava3.core.Observable
import tw.north27.coachingapp.base.BaseViewModel
import tw.north27.coachingapp.chat.*
import tw.north27.coachingapp.ext.asLiveData
import tw.north27.coachingapp.media.mediaStore.Media
import tw.north27.coachingapp.media.mediaStore.MediaAlbum
import tw.north27.coachingapp.media.mediaStore.MediaAlbumSetting
import tw.north27.coachingapp.media.mediaStore.MimeType

class MediaViewModel(private val repo: IMediaRepository) : BaseViewModel() {

    fun getMediaAudio(): Observable<List<MediaAlbum>> {
        return repo.getMediaAudio(audioSetting)
    }

    fun getMediaImage(): Observable<List<MediaAlbum>> {
        return repo.getMediaImage(imageSetting)
    }

    fun getMediaVideo(): Observable<List<MediaAlbum>> {
        return repo.getMediaVideo(videoSetting)
    }

    val audioSetting = MediaAlbumSetting(
        mimeType = MimeType.AUDIO,
        isMultipleChoice = true,
        maxCount = 2,
        audioMinDuration = 0,
        audioMaxDuration = 60000//1m
    )

    val imageSetting = MediaAlbumSetting(
        mimeType = MimeType.IMAGE,
        isMultipleChoice = true,
        maxCount = 6
    )

    val videoSetting = MediaAlbumSetting(
        mimeType = MimeType.VIDEO,
        isMultipleChoice = true,
        maxCount = 3,
        videoMinDuration = 0,//m
        videoMaxDuration = 60000//1m
    )

    /**
     * 全部媒體項目
     * */

    private val _mediaList = MutableLiveData<MutableList<Media>?>()

    val mediaList = _mediaList.asLiveData()

    val choiceMediaList = mediaList.map { it?.filter { it.isChoice } }

    fun setMediaList(mediaList: MutableList<Media>?) {
        _mediaList.value = mediaList
    }

    fun setChoiceOfMedia(media: Media) {
        _mediaList.value = mediaList.value?.also {
            it.find { media.id == it.id }?.isChoice = media.isChoice
        }
    }

}