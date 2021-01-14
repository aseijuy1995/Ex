package edu.yujie.socketex.album

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject

class GalleryViewModel : ViewModel(), KoinComponent {
    private val setting: AlbumSetting? = null
    private val multipleSelectMedia = BehaviorSubject.createDefault<MutableList<Media>>(mutableListOf())

    private val singleSelectMedia = PublishSubject.create<Media>()
    private val currentAlbum = BehaviorSubject.create<AlbumItem>()
    private val albumRepo by inject<IAlbumRepow>()

    fun loadAlbum(): Completable = albumRepo.fetchAlbums(setting).doOnComplete {
        selectAlbum(
            albumRepo.getAlbumItemSync(ALL_MEDIA_ALBUM_NAME, setting)!!
        )
    }

    fun selectAlbum(albumItem: AlbumItem) = currentAlbum.onNext(albumItem)

    fun selectMedia(media: Media) {
        if (setting?.multipleSelection == true) {
            multipleSelectMedia.value?.run {
                if (this.contains(media))
                    this.remove(media)
                else {
                    setting.maxSelection.let {
                        if (this.size < it) this.add(media)
                    } ?: kotlin.run {
                        this.add(media)
                    }
                }
                multipleSelectMedia.onNext(this)
            }
        } else {
            singleSelectMedia.onNext(media)
        }
    }

    fun isSelect(media: Media): Boolean = multipleSelectMedia.value!!.contains(media)


}