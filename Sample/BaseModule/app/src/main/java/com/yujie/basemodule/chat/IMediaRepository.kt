package tw.north27.coachingapp.chat

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import tw.north27.coachingapp.media.RecorderSetting
import tw.north27.coachingapp.media.mediaStore.MediaAlbum
import tw.north27.coachingapp.media.mediaStore.MediaAlbumSetting

interface IMediaRepository {

    fun getMediaAudio(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>>

    fun getMediaImage(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>>

    fun getMediaVideo(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun audioDecodeToPcm(filePath: String, pcmPath: String)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    val recordingTime: PublishRelay<Long>

    fun prepareRecording(setting: RecorderSetting)

    fun startRecording()

    fun stopRecording()

    fun releaseRecording()

}