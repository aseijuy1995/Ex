package tw.north27.coachingapp.media

import android.media.*
import android.view.Surface
import timber.log.Timber
import java.io.File
import java.io.IOException

interface IVideoMediaCodecModule {

    companion object {
        val AUDIO = "audio/"

        val VIDEO = "video/"
    }

    fun addSource(file: File): IVideoMediaCodecModule

    fun extract(mime: String): IVideoMediaCodecModule

    fun decode(setting: DecodeSetting): IVideoMediaCodecModule

    fun encoder(setting: EncodeSetting): IVideoMediaCodecModule

}

class VideoMediaCodecModule : IVideoMediaCodecModule {

    private var mediaExtractor: MediaExtractor? = null
    private var mediaFormat: MediaFormat? = null
    private var mimeType: String? = null

    override fun addSource(file: File): IVideoMediaCodecModule {
        mediaExtractor = MediaExtractor()
        try {
            mediaExtractor!!.setDataSource(file.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    override fun extract(mime: String): IVideoMediaCodecModule {
        if (mediaExtractor == null) throw NullPointerException("MediaExtractor has not been created")
        (0..mediaExtractor!!.trackCount).forEach { index ->
            mediaFormat = mediaExtractor!!.getTrackFormat(index)
            mimeType = mediaFormat!!.getString(MediaFormat.KEY_MIME)!!
            if (mimeType!!.startsWith(mime)) {
                mediaExtractor!!.selectTrack(index)
            }
        }
        return this
    }

    override fun decode(setting: DecodeSetting): IVideoMediaCodecModule {
        try {
            val mimeType = setting.mediaFormat.getString(MediaFormat.KEY_MIME)!!
            //
            val mediaDecoder: MediaCodec = MediaCodec.createDecoderByType(mimeType)

            mediaDecoder.configure(setting.mediaFormat, setting.surface, setting.crypto, 0)
            mediaDecoder.start()
            //
            val bufferInfo = MediaCodec.BufferInfo()
            //
            var sampleSize = 0
            while (sampleSize >= 0) {
                //input
                //獲取可輸入的緩衝區索引值
                mediaDecoder.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputIndex ->
                    val inputByteBuffer = mediaDecoder.getInputBuffer(inputIndex)!!//獲取可輸入的緩衝區
                    inputByteBuffer.clear()
                    sampleSize = mediaExtractor!!.readSampleData(inputByteBuffer, 0)//將分離出的樣本讀出流至指定的緩衝區中
                    //輸入的緩衝區中若有數據，則排至codec中開始解碼
                    if (sampleSize >= 0) {
                        val sampleTime = mediaExtractor!!.sampleTime
                        mediaDecoder.queueInputBuffer(inputIndex, 0, sampleSize, sampleTime, 0)
                        mediaExtractor!!.advance()
                        //輸入的緩衝區中若為空時，需發送BUFFER_FLAG_END_OF_STREAM給codec
                    } else {
                        mediaDecoder.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    }
                } ?: throw NullPointerException("Input buffer not available")
                //output
                //獲取輸出緩衝區的索引值
                val outputIndex = mediaDecoder.dequeueOutputBuffer(bufferInfo, -1)
                //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
                if (outputIndex > 0) {
                    val outputByteBuffer = mediaDecoder.getOutputBuffer(outputIndex)

                    //MediaFormat格式轉換
                } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    val mediaFormat = mediaDecoder.getOutputFormat(outputIndex)
                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
                    //超時
                } else if (outputIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return this
    }

    override fun encoder(setting: EncodeSetting): IVideoMediaCodecModule {
        val mediaEncoder = createEncoder(setting) ?: throw NullPointerException("MediaEncoder has not been created")


        return this

    }

    private fun createEncoder(setting: EncodeSetting): MediaCodec? {
        try {
            val mediaFormat = MediaFormat.createVideoFormat(setting.mimeType, setting.width, setting.height)
            mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, setting.colorFormat)//顏色格式
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, setting.bitRate)//bit - 比特率（以位/秒為單位）
            mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, setting.frameRate)//fps - 幀速率（以幀/秒為單位）
            mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, setting.iFrameInterval)//關鍵幀頻率的關鍵幀
            val mediaEncoder = MediaCodec.createEncoderByType(setting.mimeType)
            mediaEncoder.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            return mediaEncoder
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}

data class DecodeSetting(
    val mediaFormat: MediaFormat,
    val surface: Surface?,
    val crypto: MediaCrypto?
)

data class EncodeSetting(
    val mimeType: String = "video/avc",
    val width: Int,
    val height: Int,
    val colorFormat: Int = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible,
    //原始視頻格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
    //YUV格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
    val bitRate: Int = 600,
    val frameRate: Int = 30,
    val iFrameInterval: Int = 1//?

)