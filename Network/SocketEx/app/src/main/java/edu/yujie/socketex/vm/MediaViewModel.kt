package edu.yujie.socketex.vm

import android.app.Application
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.bean.MimeType
import edu.yujie.socketex.finish.base.viewModel.BaseAndroidViewModel
import edu.yujie.socketex.inter.IMediaRepo
import edu.yujie.socketex.util.asLiveData
import edu.yujie.socketex.util.mutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaViewModel(application: Application, private val repo: IMediaRepo) : BaseAndroidViewModel(application) {

    private val _toast = mutableLiveData<String>("")

    val toast = _toast.asLiveData()

    val selectMedias = mutableListOf<Media>()

    private val _selectMediaList = mutableLiveData<List<Media>>(selectMedias)

    val selectMediaList = _selectMediaList.asLiveData()

    val mediaListRelay = BehaviorRelay.create<List<Media>>()//last list

    fun getMediaAlbumItems(setting: MediaSetting): Observable<List<MediaAlbumItem>> = repo.getMediaAlbumItems(setting = setting)
        .map {
            if (it.isEmpty()) {
                _toast.postValue("No ${setting.mimeType}")
                viewModelScope.launch {
                    delay(500)
                    _toast.postValue("")
                }
            }
            it
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    //isPick:表選擇中
    fun selectMedia(media: Media, isPick: Boolean = false) {
        if (media.isSelect)
            selectMedias.add(media)
        else {
            selectMedias.remove(media)
        }
        _selectMediaList.value = selectMedias
        if (isPick) _media.value = media
    }

    fun cleanMediaList() {
        selectMedias.clear()
        _selectMediaList.value = selectMedias
    }

    fun sendMediaList() {
        mediaListRelay.accept(selectMedias)
    }

    //--------------------------------------------------------------------------------------
    private val _media = mutableLiveData<Media>()

    val media = _media.asLiveData()

    val isMimeTypeImage = _media.map { it.mimeType.startsWith(MimeType.IMAGE.toString()) }

    val isMimeTypeVideo = _media.map { it.mimeType.startsWith(MimeType.VIDEO.toString()) }

    fun buildPlayer(media: Media): Player {
        val mediaItem = MediaItem.fromUri(media.data)
        return SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            play()
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
}