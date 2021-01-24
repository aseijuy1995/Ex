package edu.yujie.socketex.inter

import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IMediaRepo {

    fun fetchMedia(setting: MediaSetting): Completable

    fun getMediaAlbumItems(setting: MediaSetting): PublishRelay<List<MediaAlbumItem>>

    fun getMediaAlbumItemFromAlbumName(albumName: String, setting: MediaSetting): Observable<MediaAlbumItem?>

    fun getMediaAlbumItemSync(albumName: String, setting: MediaSetting): MediaAlbumItem?

}