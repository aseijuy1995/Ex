package edu.yujie.socketex.inter

import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.MediaAlbumItem
import edu.yujie.socketex.bean.MediaSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IMedia {

    fun fetchMediaDatas(setting: MediaSetting?): Completable

    fun getMediaItems(setting: MediaSetting?): BehaviorRelay<List<MediaAlbumItem>>

    fun getMediaItem(name: String, setting: MediaSetting?): Observable<MediaAlbumItem>

    fun getMediaItemSync(name: String, setting: MediaSetting?): MediaAlbumItem?

}