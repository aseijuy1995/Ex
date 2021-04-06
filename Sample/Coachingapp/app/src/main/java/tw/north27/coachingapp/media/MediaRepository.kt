package tw.north27.coachingapp.media

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import tw.north27.coachingapp.chat.IMediaRepository
import tw.north27.coachingapp.chat.MediaAlbum
import tw.north27.coachingapp.chat.MediaSetting

class MediaRepository(
    private val imageModule: IMediaModule,
    private val videoModule: IMediaModule,
    private val audioModule: IMediaModule,
    private val codecModule: IMediaCodecModule,
    private val recorderModule: IMediaRecorderModule,
) : IMediaRepository {

    override fun getMediaAudio(setting: MediaSetting): Observable<List<MediaAlbum>> =
        audioModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaImage(setting: MediaSetting): Observable<List<MediaAlbum>> =
        imageModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaVideo(setting: MediaSetting): Observable<List<MediaAlbum>> =
        videoModule.getMediaAlbum(setting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override suspend fun audioDecodeToPcm(filePath: String, pcmPath: String) {
        codecModule
            .addSource(filePath)
            .outputPcm(pcmPath)
            .createDecoder()
            .decodeData()
    }

    override val recordingTime: PublishRelay<Long>
        get() = recorderModule.timeRelay

    override fun prepareRecording(setting: RecorderSetting) {
        recorderModule.prepare(setting)
    }

    override fun startRecording() {
        recorderModule.start()
    }

    override fun stopRecording() {
        recorderModule.stop()
    }

    override fun releaseRecording() {
        recorderModule.release()
    }
}