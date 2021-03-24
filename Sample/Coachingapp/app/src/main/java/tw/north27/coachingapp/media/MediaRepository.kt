package tw.north27.coachingapp.media

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import tw.north27.coachingapp.chat.IMediaRepository
import tw.north27.coachingapp.chat.MediaAlbum
import tw.north27.coachingapp.chat.MediaSetting

class MediaRepository(
    private val imagesModule: IMediaModule,
    private val videoModule: IMediaModule,
    private val audioModule: IMediaModule
) : IMediaRepository {

    override fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>> =
        audioModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>> =
        imagesModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>> =
        videoModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}