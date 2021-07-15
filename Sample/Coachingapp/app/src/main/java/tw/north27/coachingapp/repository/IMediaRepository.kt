package tw.north27.coachingapp.repository

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import tw.north27.coachingapp.model.MediaAlbum
import tw.north27.coachingapp.model.MediaSetting

interface IMediaRepository {

    fun getMediaAlbum(setting: MediaSetting): Observable<List<MediaAlbum>>

    fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>>

    fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>>

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