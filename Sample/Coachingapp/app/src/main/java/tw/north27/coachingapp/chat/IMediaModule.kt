package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IMediaModule {

    fun fetchMedia(setting: MediaSetting): Completable

//    fun getAlbumItems(setting: MediaSetting): PublishRelay<List<MediaAlbumItem>>
//
//    fun getAlbumItemFromAlbumName(albumName: String, setting: MediaSetting): Observable<MediaAlbumItem?>
//
//    fun getAlbumItemSync(albumName: String, setting: MediaSetting): MediaAlbumItem?

}