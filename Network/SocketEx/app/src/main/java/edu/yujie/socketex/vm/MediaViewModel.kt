package edu.yujie.socketex.vm

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.base.BaseViewModel
import edu.yujie.socketex.bean.ALL_MEDIA_ALBUM_NAME
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.inter.IMediaRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MediaViewModel(private val repo: IMediaRepo) : BaseViewModel() {

    private var setting: MediaSetting = MediaSetting()

    val multipleSelectMediaRelay = BehaviorRelay.createDefault<MutableList<Media>>(mutableListOf<Media>())

    val singleSelectMediaRelay = PublishRelay.create<Media>()

    val currentMediaAlbumItemRelay = BehaviorRelay.create<MediaAlbumItem>()

    val toastRelay = PublishRelay.create<String>()

    fun loadMedia(): Completable {
        return repo.fetchMediaDatas(setting = setting)
            .doOnComplete {
                repo.getMediaItemSync(albumName = ALL_MEDIA_ALBUM_NAME, setting = setting)
            }
    }

    fun selectMediaAlbumItem(mediaAlbumItem: MediaAlbumItem) = currentMediaAlbumItemRelay.accept(mediaAlbumItem)

    fun selectMedia(media: Media) {
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

    fun getMediaAlbumItems(setting: MediaSetting): Observable<List<MediaAlbumItem>> = repo.getMediaAlbumItems(setting = setting)
        .map {
            if (it.size <= 0) toastRelay.accept("No ${setting.mimeType}")
            it
        }

    fun getMediaAlbumItem(albumName: String): Observable<MediaAlbumItem> = repo.getMediaAlbumItem(albumName = albumName, setting = setting)
}