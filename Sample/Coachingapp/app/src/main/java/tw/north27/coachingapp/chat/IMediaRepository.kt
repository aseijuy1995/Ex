package tw.north27.coachingapp.chat

import com.yujie.myapplication.MediaAlbum
import com.yujie.myapplication.MediaSetting
import io.reactivex.rxjava3.core.Observable

interface IMediaRepository {

    fun getMediaImages(setting: MediaSetting): Observable<List<MediaAlbum>>
}