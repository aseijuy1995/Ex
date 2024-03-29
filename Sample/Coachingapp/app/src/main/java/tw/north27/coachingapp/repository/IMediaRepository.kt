package tw.north27.coachingapp.repository

import com.yujie.core_lib.model.Media
import com.yujie.core_lib.model.MediaSetting
import kotlinx.coroutines.flow.Flow

interface IMediaRepository {

    suspend fun fetchMediaImage(setting: MediaSetting): Flow<List<Media>>

    suspend fun fetchMediaVideo(setting: MediaSetting): Flow<List<Media>>

    suspend fun fetchMediaAudio(setting: MediaSetting): Flow<List<Media>>

//    fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>>
//
//    fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    fun audioDecodeToPcm(filePath: String, pcmPath: String)
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    val recordingTime: PublishRelay<Long>
//
//    fun prepareRecording(setting: RecorderSetting)
//
//    fun startRecording()
//
//    fun stopRecording()
//
//    fun releaseRecording()

}