package edu.yujie.socketex.album

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface IAlbumRepow {
    fun fetchAlbums(setting: AlbumSetting? = null): Completable

    fun getAlbums(setting: AlbumSetting? = null): BehaviorSubject<List<AlbumItem>>

    fun getAlbumItem(name: String, setting: AlbumSetting? = null): Observable<AlbumItem>

    fun getAlbumItemSync(name: String, settings: AlbumSetting? = null): AlbumItem?
}