package edu.yujie.socketex.vm

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.base.BaseViewModel
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.inter.IMediaRepo
import edu.yujie.socketex.util.asLiveData
import edu.yujie.socketex.util.mutableLiveData
import io.reactivex.rxjava3.core.Observable

class MediaViewModel(private val repo: IMediaRepo) : BaseViewModel() {

    private var setting: MediaSetting = MediaSetting()

    val toastRelay = PublishRelay.create<String>()

    //
    private val selectMedias = mutableListOf<Media>()

    private val _selectMediaList: MutableLiveData<List<Media>> by lazy { MutableLiveData<List<Media>>() }

    val selectMediaList = _selectMediaList.asLiveData()

    val mediaListRelay = PublishRelay.create<List<Media>>()

    //
    fun getMediaAlbumItems(setting: MediaSetting): Observable<List<MediaAlbumItem>> = repo.getMediaAlbumItems(setting = setting)
        .map {
            if (it.size <= 0) toastRelay.accept("No ${setting.mimeType}")
            it
        }

    //
    fun selectMedia(media: Media) {
        if (media.isSelect != null && media.isSelect!!) {
            selectMedias.add(media)
        } else {
            selectMedias.remove(media)
        }
        _selectMediaList.value = selectMedias
    }

    fun isSelectMedia(): Boolean = selectMedias.size > 0

    fun sendMedia() {
        mediaListRelay.accept(selectMediaList.value)
        cleanMediaStorage()
    }

    fun cleanMediaStorage() {
        _selectMediaList.postValue(listOf())
        selectMedias.clear()
    }

    //
//    private val _media: MutableLiveData<Media> by lazy { MutableLiveData<Media>() }

    private val _media = mutableLiveData<Media>()

    val media = _media.asLiveData()

    fun setMedia(media: Media) {
        _media.value = media
    }

    //


    //
    val multipleSelectMediaRelay = BehaviorRelay.createDefault<MutableList<Media>>(mutableListOf<Media>())

    val singleSelectMediaRelay = PublishRelay.create<Media>()

//    val currentMediaAlbumItemRelay = BehaviorRelay.create<MediaAlbumItem>()

    //


    fun getMediaAlbumItem(albumName: String): Observable<MediaAlbumItem> = repo.getMediaAlbumItem(albumName = albumName, setting = setting)

    fun selectMediaListState(mediaList: List<Media>) {
//        val mediaList = getMediaList(mediaAlbumItemList)
        mediaList.any { it.isSelect != null && it.isSelect == true }
    }


//    fun getMediaList(mediaList: List<Media>): List<Media> = mediaAlbumItemList.flatMap { it.mediaList }


    //
    fun selectMedia2(media: Media) {
        if (setting.isMultipleSelection) {
            multipleSelectMediaRelay.value?.run {
                if (this.contains(media)) {
                    this.remove(media)
                } else {
                    setting.maxSelectionCount?.let {
                        if (it < this.size) {
                            this.add(media)
                        }
                    } ?: kotlin.run {
                        this.add(media)
                    }
                }
            }
        } else {
            singleSelectMediaRelay.accept(media)
        }
    }

    fun isSelect(media: Media): Boolean = multipleSelectMediaRelay.value.contains(media)
    //

//    fun loadMedia(): Completable {
//        return repo.fetchMediaDatas(setting = setting)
//            .doOnComplete {
//                repo.getMediaItemSync(albumName = ALL_MEDIA_ALBUM_NAME, setting = setting)
//            }
//    }

//    fun selectMediaAlbumItem(mediaAlbumItem: MediaAlbumItem) = currentMediaAlbumItemRelay.accept(mediaAlbumItem)


}