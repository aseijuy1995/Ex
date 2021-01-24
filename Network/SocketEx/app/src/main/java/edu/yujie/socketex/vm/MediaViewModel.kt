package edu.yujie.socketex.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
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

    private val currentSelectMediaList = mutableListOf<Media>()

    private val _selectMediaList = mutableLiveData<List<Media>>(currentSelectMediaList)

    val selectMediaList = _selectMediaList.asLiveData()

    val mediaListRelay = PublishRelay.create<List<Media>>()

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

    fun selectMedia(media: Media) {
        if (media.isSelect != null && media.isSelect!!) currentSelectMediaList.add(media) else currentSelectMediaList.remove(media)
        _selectMediaList.value = currentSelectMediaList
        _media.value = media
    }

    fun cleanSelectMediaList() {
        currentSelectMediaList.clear()
        _selectMediaList.value = currentSelectMediaList
    }

    fun sendSelectMediaList() = mediaListRelay.accept(selectMediaList.value)

    //--------------------------------------------------------------------------------------
    private val _media = mutableLiveData<Media>()

    val media = _media.asLiveData()

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