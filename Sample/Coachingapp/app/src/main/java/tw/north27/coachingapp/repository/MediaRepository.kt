package tw.north27.coachingapp.repository

import com.yujie.utilmodule.media.IMediaStoreModule
import com.yujie.utilmodule.media.model.Media
import com.yujie.utilmodule.media.model.MediaSetting
import kotlinx.coroutines.flow.Flow

class MediaRepository(
    private val imageStoreModule: IMediaStoreModule,
//    private val videoStoreModule: IMediaStoreModule,
//    private val audioStoreModule: IMediaStoreModule,
//    private val codecModule: IAudioMediaCodecModule,
//    private val recorderModule: IMediaRecorderModule
) : IMediaRepository {

    override fun fetchMediaImage(setting: MediaSetting): Flow<List<Media>> = imageStoreModule.fetchMediaList(setting)

//    override fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>> =
//        audioStoreModule.getMediaAlbum(setting)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//    override fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>> =
//        videoStoreModule.getMediaAlbum(setting)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    override suspend fun audioDecodeToPcm(filePath: String, pcmPath: String) {
//        codecModule
//            .addSource(filePath)
//            .outputPcm(pcmPath)
//            .createDecoder()
//            .decodeData()
//    }
//
//    override val recordingTime: PublishRelay<Long>
//        get() = recorderModule.timeRelay
//
//    override fun prepareRecording(setting: RecorderSetting) {
//        recorderModule.prepare(setting)
//    }
//
//    override fun startRecording() {
//        recorderModule.start()
//    }
//
//    override fun stopRecording() {
//        recorderModule.stop()
//    }
//
//    override fun releaseRecording() {
//        recorderModule.release()
//    }

}