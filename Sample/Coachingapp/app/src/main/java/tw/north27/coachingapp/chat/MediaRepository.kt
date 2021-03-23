package tw.north27.coachingapp.chat

import com.yujie.myapplication.MediaAlbum
import com.yujie.myapplication.MediaSetting
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class MediaRepository(private val mediaImagesModule: IMediaImagesModule) : IMediaRepository {

    override fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>> =
        mediaImagesModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}