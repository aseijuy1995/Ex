package tw.north27.coachingapp.chat

import io.reactivex.rxjava3.core.Observable

interface IMediaRepository {

    fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>>

    fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>>

    fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>>
}