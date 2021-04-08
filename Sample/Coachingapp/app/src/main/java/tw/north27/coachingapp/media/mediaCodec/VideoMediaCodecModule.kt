package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber
import tw.north27.coachingapp.media.DecodeSetting
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class VideoMediaCodecModule private constructor() : IMediaCodecModule {

    private var setting: DecodeSetting? = null

    private val inputPath: String
        get() = setting?.inputPath ?: throw NullPointerException("Can't find InputPath!")

    private val outputPath: String
        get() = setting?.outputPath ?: throw NullPointerException("Can't find OutputPath!")

    private val videoMediaExtractorModule: MediaExtractorModule = MediaExtractorModule()

    private val mediaFormat: MediaFormat
        get() = videoMediaExtractorModule.mediaFormat ?: throw NullPointerException("Can't find MediaFormat!")

    private val mimeType: String
        get() = videoMediaExtractorModule.mimeType ?: throw NullPointerException("Can't find MimeType!")

    private var mediaExtractor: MediaExtractor? = null

    private var mediaDecoder: MediaCodec? = null

    companion object {
        fun create(): IMediaCodecModule {
            return VideoMediaCodecModule()
        }
    }

    override fun configDecoder(setting: DecodeSetting): IMediaCodecModule {
//        clearDecoder()
        //
        this.setting = setting
        mediaExtractor = videoMediaExtractorModule.extract(inputPath, IMediaExtractorModule.MimeType.VIDEO)
        try {
            mediaDecoder = MediaCodec.createDecoderByType(mimeType)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaDecoder!!.configure(mediaFormat, setting.surface, setting.crypto, 0)
        return this
    }

    private fun clearDecoder() {
        this.setting = null
        this.mediaExtractor = null
        this.mediaDecoder = null
    }

    //    override suspend fun decode() {
    override fun decode() {
        if (mediaExtractor == null) throw NullPointerException("Can't find MediaExtractor!")
        if (mediaDecoder == null) throw NullPointerException("Can't find MediaDecoder!")
        mediaDecoder!!.start()
        //
        val bufferInfo = MediaCodec.BufferInfo()
        //
        val outputFile = File(outputPath)
        val fileChannel = FileOutputStream(outputFile).channel
        //
        var isStop = false
        while (!isStop) {
            //input
            //獲取可輸入的緩衝區索引值
            mediaDecoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputIndex ->
                val inputByteBuffer = mediaDecoder!!.getInputBuffer(inputIndex)!!//獲取可輸入的緩衝區
                inputByteBuffer.clear()
                val sampleSize = mediaExtractor!!.readSampleData(inputByteBuffer, 0)//將分離出的樣本讀出流至指定的緩衝區中
                //輸入的緩衝區中若有數據，則排至codec中開始解碼
                Timber.d("sampleSize = $sampleSize")
                if (sampleSize >= 0) {
                    val sampleTime = mediaExtractor!!.sampleTime
                    mediaDecoder!!.queueInputBuffer(inputIndex, 0, sampleSize, sampleTime, 0)
                    mediaExtractor!!.advance()
                    //輸入的緩衝區中若為空時，需發送BUFFER_FLAG_END_OF_STREAM給codec
                } else {
                    mediaDecoder!!.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    Timber.d("queueInputBuffer = 2")
                    isStop = true
                }
            } ?: throw NullPointerException("Input buffer not available")
            //output
            //獲取輸出緩衝區的索引值
            val outputIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
            Timber.d("outputIndex = $outputIndex")
            if (outputIndex > 0) {
                val outputByteBuffer = mediaDecoder!!.getOutputBuffer(outputIndex)
                try {
                    fileChannel.write(outputByteBuffer)
                } catch (e: IOException) {
                    Timber.e(e)
                    e.printStackTrace()
                }
                //MediaFormat格式轉換
            } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                val mediaFormat = mediaDecoder!!.getOutputFormat(outputIndex)
                Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
                //超時
            } else if (outputIndex == MediaCodec.INFO_TRY_AGAIN_LATER) {
                Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
            }
        }
        try {
            fileChannel.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaDecoder?.stop()
        mediaDecoder?.release()
        mediaExtractor?.release()

    }

}