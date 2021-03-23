package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.myapplication.MediaAlbum
import com.yujie.myapplication.MediaSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

//images
const val MEDIA_ALBUM_IMAGES = "MEDIA_ALBUM_IMAGES"

interface IMediaImagesModule {
    /**
     * 獲取媒體相關資訊
     * */
    fun fetchMediaImages(setting: MediaSetting): Completable

    /**
     * 獲取媒體專輯
     * */
    fun getMediaAlbum(setting: MediaSetting): PublishRelay<List<MediaAlbum>>

    /**
     * 獲取指定媒體專輯
     * MEDIA_ALBUM_IMAGES：圖片
     * */
    fun getAlbumFromName(albumName: String, setting: MediaSetting): Observable<MediaAlbum?>

    /**
     * 獲取指定媒體專輯
     * MEDIA_ALBUM_IMAGES：圖片
     * */
    fun getAlbumFromNameSync(albumName: String, setting: MediaSetting): MediaAlbum?

}