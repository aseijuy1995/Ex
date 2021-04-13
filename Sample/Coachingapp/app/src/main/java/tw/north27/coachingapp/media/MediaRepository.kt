package tw.north27.coachingapp.media

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import tw.north27.coachingapp.chat.IMediaRepository
import tw.north27.coachingapp.media.mediaCodec.CodecSetting
import tw.north27.coachingapp.media.mediaCodec.VideoCompressModule
import tw.north27.coachingapp.media.mediaStore.IMediaStoreModule
import tw.north27.coachingapp.media.mediaStore.MediaAlbum
import tw.north27.coachingapp.media.mediaStore.MediaAlbumSetting

class MediaRepository(
    private val imageStoreModule: IMediaStoreModule,
    private val videoStoreModule: IMediaStoreModule,
    private val audioStoreModule: IMediaStoreModule,
    private val codecModule: IAudioMediaCodecModule,
    private val recorderModule: IMediaRecorderModule
) : IMediaRepository {

    override fun getMediaAudio(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>> =
        audioStoreModule.getMediaAlbum(albumSetting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaImage(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>> =
        imageStoreModule.getMediaAlbum(albumSetting)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMediaVideo(albumSetting: MediaAlbumSetting): Observable<List<MediaAlbum>> =
        videoStoreModule.getMediaAlbum(albumSetting)
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