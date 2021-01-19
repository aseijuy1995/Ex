package edu.yujie.socketex.vm

import android.app.Application
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.base.BaseAndroidViewModel
import edu.yujie.socketex.bean.ALL_MEDIA_ALBUM_NAME
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import edu.yujie.socketex.inter.IMedia
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class MediaViewModel(application: Application, private val repo: IMedia) : BaseAndroidViewModel(application) {
    private val setting: MediaSetting = MediaSetting()

    val multipleSelectMediaRelay = BehaviorRelay.createDefault<MutableList<Media>>(mutableListOf<Media>())

    val singleSelectMediaRelay = PublishRelay.create<Media>()

    val currentMediaAlbumItemRelay = BehaviorRelay.create<MediaAlbumItem>()

    fun loadMedia(): Completable {
        return repo.fetchMediaDatas(setting = setting)
            .doOnComplete {
                repo.getMediaItemSync(name = ALL_MEDIA_ALBUM_NAME, setting = setting)
            }
    }

    fun getMediaItem(name: String): Observable<MediaAlbumItem> = repo.getMediaItem(name = name, setting = setting)

    fun selectMediaAlbumItem(mediaAlbumItem: MediaAlbumItem) = currentMediaAlbumItemRelay.accept(mediaAlbumItem)

    fun selectMedia(media: Media) {
        if (setting.isMultipleSelection) {
            multipleSelectMediaRelay.value?.run {
                if (this.contains(media)) {
                    this.remove(media)
                } else {
                    setting.maxCount?.let {
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

    fun getMediaItems(): BehaviorRelay<List<MediaAlbumItem>> = repo.getMediaItems(setting)
}