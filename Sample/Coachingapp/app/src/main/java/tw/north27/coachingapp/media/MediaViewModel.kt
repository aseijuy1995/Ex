package tw.north27.coachingapp.media

import android.app.Application
import io.reactivex.rxjava3.core.Observable
import tw.north27.coachingapp.base.BaseAndroidViewModel
import tw.north27.coachingapp.chat.IMediaRepository
import tw.north27.coachingapp.chat.MediaAlbum
import tw.north27.coachingapp.chat.MediaSetting
import tw.north27.coachingapp.chat.MimeType

class MediaViewModel(application: Application, private val repo: IMediaRepository) : BaseAndroidViewModel(application) {

    fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>> {
        return repo.getMediaAudio(setting)
    }

    fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>> {

        return repo.getMediaImages(setting)
    }

    fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>> {
        return repo.getMediaVideo(setting)
    }

//    private val _toast = MutableLiveData<String>()
//
//    val toast = _toast.asLiveData()
//
//    val selectMedias = MutableLiveData<Media>()
//
//    private val _selectMediaList = MutableLiveData<List<Media>>(selectMedias)
//
//    val selectMediaList = _selectMediaList.asLiveData()
//
//    val mediaListRelay = BehaviorRelay.create<List<Media>>()//last list


//    //isPick:表選擇中
//    fun selectMedia(media: Media, isPick: Boolean = false) {
//        if (media.isSelect)
//            selectMedias.add(media)
//        else {
//            selectMedias.remove(media)
//        }
//        _selectMediaList.value = selectMedias
//        if (isPick) _media.value = media
//    }
//
//    fun cleanMediaList() {
//        selectMedias.clear()
//        _selectMediaList.value = selectMedias
//    }
//
//    fun sendMediaList() {
//        mediaListRelay.accept(selectMedias)
//    }
//
//    //--------------------------------------------------------------------------------------
//    private val _media = mutableLiveData<Media>()
//
//    val media = _media.asLiveData()
//
//    val isMimeTypeImage = _media.map { it.mimeType.startsWith(MimeType.IMAGE.toString()) }
//
//    val isMimeTypeVideo = _media.map { it.mimeType.startsWith(MimeType.VIDEO.toString()) }
//
//    fun buildPlayer(media: Media): Player {
//        val mediaItem = MediaItem.fromUri(media.data)
//        return SimpleExoPlayer.Builder(context).build().apply {
//            setMediaItem(mediaItem)
//            repeatMode = Player.REPEAT_MODE_ALL
//            prepare()
//            play()
//        }
//    }

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
}