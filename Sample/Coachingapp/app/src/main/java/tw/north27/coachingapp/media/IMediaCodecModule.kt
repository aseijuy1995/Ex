package tw.north27.coachingapp.media

import android.media.MediaCodec
import android.media.MediaCodecList
import android.media.MediaFormat
import android.os.Build
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber
import java.io.IOException

interface IMediaCodecModule {

    fun createDecoder(mimeType: String)

//    fun createEncoder(mimeType: String)
//
//    fun createCodec(name: String)
//
//    fun getMediaEncoder()


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


//MediaExtractor.getTrackFormat。
//MediaFormat.setFeatureEnabled
//MediaCodecList.findDecoderForFormat
//createByCodecName(String)。

class MediaCodecModule : IMediaCodecModule {

    override fun createDecoder(mimeType: String) {
        try {
            val mediaCodec = MediaCodec.createDecoderByType(mimeType)
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            mediaCodec.start()
        } catch (e: IOException) {
            Timber.e("IOException: ${e.message}")
        }
    }

//    override fun createEncoder(mimeType: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun createCodec(name: String) {
//        TODO("Not yet implemented")
//    }
//
//
//    override fun getMediaEncoder() {
//        val mediaCodecList = MediaCodecList(MediaCodecList.REGULAR_CODECS)//適用於常規（緩衝區到緩衝液中）解碼或編碼的編解碼器
//        val mediaCodecInfos = mediaCodecList.codecInfos
//        val encoderStr = mediaCodecList.findEncoderForFormat(mediaFormat)
//        for (mediaCodecInfo in mediaCodecInfos) {
//            if (mediaCodecInfo.isEncoder) {
//                if (mediaCodecInfo.name == encoderStr) {
//
//                }
//            }
//        }
//    }

    override var mediaCodec: MediaCodec? = null

    val isMediaCodecInit = mediaCodec != null

    var mediaFormat: MediaFormat? = null

    override fun createMediaCodec(config: MediaCodecConfig): Completable = Completable.fromAction {
        createVideoMediaCodec(config)
//        MediaFormat.createVideoFormat()
//        MediaFormat.createAudioFormat()
//        MediaFormat.createSubtitleFormat()
    }

    private fun createVideoMediaCodec(config: MediaCodecConfig) {
        val mediaCodec = MediaCodec.createEncoderByType(config.mime)

        mediaFormat = config.format ?: MediaFormat().apply {
            setString(MediaFormat.KEY_MIME, config.mime)
            setString(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) MediaFormat.KEY_CODECS_STRING else "codecs-string", config.codesString)
            setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, config.maxInputSize)
            setInteger(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) MediaFormat.KEY_PIXEL_ASPECT_RATIO_WIDTH else "sar-width", config.pixelAspectRatioWidth)
            setInteger(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) MediaFormat.KEY_PIXEL_ASPECT_RATIO_HEIGHT else "sar-height", config.pixelAspectRatioHeight)
            setInteger(MediaFormat.KEY_BIT_RATE, config.bitRate)
            setLong(MediaFormat.KEY_DURATION, config.duration)
            //
            //            setLong(MediaFormat.KEY_WIDTH, config.duration)
            //            setLong(MediaFormat.KEY_HEIGHT, config.duration)
            //            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
            //            setLong(MediaFormat.KEY_FRAME_RATE, config.duration)
            //            setLong(MediaFormat.KEY_CAPTURE_RATE, config.duration)
            //            setLong(MediaFormat.KEY_I_FRAME_INTERVAL, config.duration)
            //            setLong(MediaFormat.KEY_INTRA_REFRESH_PERIOD, config.duration)
            //            setLong(MediaFormat.KEY_LATENCY, config.duration)
            //            setLong(MediaFormat.KEY_MAX_WIDTH, config.duration)
            //            setLong(MediaFormat.KEY_MAX_HEIGHT, config.duration)
            //            setLong(MediaFormat.KEY_REPEAT_PREVIOUS_FRAME_AFTER, config.duration)
            //            setLong(MediaFormat.KEY_PUSH_BLANK_BUFFERS_ON_STOP, config.duration)
            //            setLong(MediaFormat.KEY_TEMPORAL_LAYERING, config.duration)
        }
        mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        this.mediaCodec = mediaCodec
    }

    override fun start() {
        try {
            mediaCodec!!.createInputSurface()//請求Surface代替輸入緩衝區用作編碼器的輸入
            mediaCodec!!.start()

            val inputBufferId = mediaCodec!!.dequeueInputBuffer(1000)
            if (inputBufferId >= 0) {
                val inputBuffer = mediaCodec?.getInputBuffer(inputBufferId)
                inputBuffer?.clear()

//                mediaCodec?.queueInputBuffer(inputBufferId, 0, ,)
            }
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("MediaCodec is surface has been released, or the format is unacceptable, or the flags are not set properly ")
        } catch (e: IllegalStateException) {
            throw IllegalStateException("MediaCodec is not in the Uninitialized state!")
        } catch (e: MediaCodec.CryptoException) {
            throw MediaCodec.CryptoException(-1, e.message)
        }
    }
//
//    override fun stop() {
//        TODO("Not yet implemented")
//    }
//
//    override fun release() {
//        TODO("Not yet implemented")
//    }
//
//    override fun reset() {
//        TODO("Not yet implemented")
//    }


}