package tw.north27.coachingapp.media

import android.media.MediaCodec
import io.reactivex.rxjava3.core.Completable

interface IMediaCodecModule {

    var mediaCodec: MediaCodec?

    fun createMediaCodec(setting: MediaCodecConfig): Completable

    fun start()
//
//    fun stop()
//
//    fun release()
//
//    fun reset()

}