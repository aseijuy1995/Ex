//package tw.north27.coachingapp.media.mediaCodec
//
//import android.media.MediaCodec
//import android.media.MediaExtractor
//import android.media.MediaFormat
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//import timber.log.Timber
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//
//class AudioCompressModule private constructor(
//    private val mediaExtractorModule: IMediaExtractorModule
//) : IMediaCodecModule {
//    private var setting: CodecSetting? = null
//
//    class Compress : IMediaCodecModule.Compress, KoinComponent {
//        private val mediaExtractorModule by inject<IMediaExtractorModule>()
//
//        private val audioCompressModule = AudioCompressModule(mediaExtractorModule)
//
//        override fun setCompress(setting: CodecSetting): IMediaCodecModule {
//            audioCompressModule.setCompressSetting(setting)
//            return audioCompressModule
//        }
//    }
//
//    private fun setCompressSetting(setting: CodecSetting): IMediaCodecModule {
//        this.setting = setting
//        return this
//    }
//
//    private val inputPath: String
//        get() = setting?.inputPath ?: throw NullPointerException("Can't find audio decode input path!")
//
//    private val outputPath: String
//        get() = setting?.outputPath ?: throw NullPointerException("Can't find audio encode output path!")
//
//    private val mimeType: String
//        get() = setting?.mimeType ?: throw NullPointerException("Can't find v encode mimeType!")
//
//    private val aacProfile: Int
//        get() = setting?.aacProfile ?: throw NullPointerException("Can't find audio encode AAC Profile!")
//
//    private val bitRate: Int
//        get() = setting?.bitRate ?: throw NullPointerException("Can't find audio encode bitRate!")
//
//    private val maxInputSize: Int
//        get() = setting?.maxInputSize ?: throw NullPointerException("Can't find audio encode maxInputSize!")
//
//    private val sampleRate: Int
//        get() = setting?.sampleRate ?: throw NullPointerException("Can't find audio encode sampleRate!")
//
//    private val channelCount: Int
//        get() = setting?.channelCount ?: throw NullPointerException("Can't find audio encode channelCount!")
//
//    //media extractor module
//    private val mediaExtractor: MediaExtractor
//        get() = mediaExtractorModule.mediaExtractor ?: throw NullPointerException("Can't find mediaExtractor!")
//
//    private val audioTrackIndex: Int
//        get() = mediaExtractorModule.audioTrackIndex ?: throw NullPointerException("Can't find audio track index!")
//
//    private val audioMediaFormat: MediaFormat
//        get() = mediaExtractorModule.audioMediaFormat ?: throw NullPointerException("Can't find audio mediaFormat!")
//
//    private val audioMimeType: String
//        get() = mediaExtractorModule.audioMimeType ?: throw NullPointerException("Can't find audio mimeType!")
//
//    //codec
//    private var mediaDecoder: MediaCodec? = null
//
//    private var mediaEncoder: MediaCodec? = null
//
//    private val outputFile: File
//        get() {
//            val file = File(outputPath)
//            if (!file.parentFile.exists())
//                file.parentFile.mkdirs()
//            if (!file.exists())
//                file.createNewFile()
//            Timber.d("exists = ${file.exists()}, file = $outputPath")
//            return file
//        }
//
//    override suspend fun compress() {
//        configDecoder()
//        configEncoder()
//        if (mediaDecoder == null) throw NullPointerException("Can't find MediaDecoder!")
//        if (mediaEncoder == null) throw NullPointerException("Can't find MediaEncoder!")
//        compressAudio()
//        //
//        close()
//    }
//
//    private fun compressAudio() {
//        mediaExtractor.selectTrack(audioTrackIndex)
//        mediaDecoder!!.start()
//        mediaEncoder!!.start()
//        //
//        val fileChannel = FileOutputStream(outputFile).channel
//        val bufferInfo = MediaCodec.BufferInfo()
//        val bufferInfo2 = MediaCodec.BufferInfo()
//        //
//        var isDecodeDone = false
//        while (!isDecodeDone) {
//            //獲取可輸入的緩衝區索引值
//            mediaDecoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { decodeInputBufferIndex ->
//                val decodeInputByteBuffer = mediaDecoder!!.getInputBuffer(decodeInputBufferIndex)!!//獲取可輸入的緩衝區
//                decodeInputByteBuffer.clear()
//                val sampleSize = mediaExtractor.readSampleData(decodeInputByteBuffer, 0)//將分離出的樣本讀出流至指定的緩衝區中
//                Timber.d("decode sampleSize = $sampleSize")
//                if (sampleSize >= 0) {
//                    //輸入的緩衝區中若有數據，則排至codec中開始解碼
//                    mediaDecoder!!.queueInputBuffer(decodeInputBufferIndex, 0, sampleSize, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
//                    mediaExtractor.advance()
//                } else {
//                    //輸入的緩衝區中數據達結尾時，發送BUFFER_FLAG_END_OF_STREAM通知codec
//                    mediaDecoder!!.queueInputBuffer(decodeInputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
//                    isDecodeDone = true
//                }
//            } ?: throw NullPointerException("Decode input buffer not available")
//            //獲取輸出緩衝區的索引值
//            val decodeOutputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
//            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
//            when {
//                decodeOutputBufferIndex >= 0 -> {
//                    val decodeOutputByteBuffer = mediaDecoder!!.getOutputBuffer(decodeOutputBufferIndex)!!
//                    if (mediaEncoder != null) {
//                        mediaEncoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
//                            val inputByteBuffer = mediaEncoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
//                            inputByteBuffer.clear()
//                            inputByteBuffer.put(decodeOutputByteBuffer)//將數據流至指定的輸入緩衝區中
//                            mediaEncoder!!.queueInputBuffer(inputBufferIndex, 0, decodeOutputByteBuffer.limit(), mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
//                            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
//                            val encodeOutputBufferIndex = mediaEncoder!!.dequeueOutputBuffer(bufferInfo2, -1)
//                            when {
//                                encodeOutputBufferIndex >= 0 -> {
//                                    val encodeOutputByteBuffer = mediaEncoder!!.getOutputBuffer(encodeOutputBufferIndex)
//                                    Timber.d("bufferInfo2.size = ${bufferInfo2.size}")
//                                    try {
//                                        fileChannel.write(encodeOutputByteBuffer)
//                                    } catch (e: IOException) {
//                                        Timber.e(e)
//                                        e.printStackTrace()
//                                    }
//                                    mediaEncoder!!.releaseOutputBuffer(encodeOutputBufferIndex, false)
//                                }
//                                encodeOutputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
//                                    //MediaFormat格式轉換
//                                    val mediaFormat = mediaDecoder!!.outputFormat
//                                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
//                                }
//                                encodeOutputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
//                                    //超時
//                                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
//                                }
//                            }
//                        } ?: throw NullPointerException("Encode input buffer not available")
//                    }
//                    mediaDecoder!!.releaseOutputBuffer(decodeOutputBufferIndex, false)
//
//                }
//                decodeOutputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
//                    //MediaFormat格式轉換
//                    val mediaFormat = mediaDecoder!!.outputFormat
//                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
//                }
//                decodeOutputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
//                    //超時
//                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
//                }
//            }
//        }
//        try {
//            fileChannel.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun close() {
//        mediaDecoder?.stop()
//        mediaDecoder?.release()
//        mediaEncoder?.stop()
//        mediaEncoder?.release()
//        mediaExtractor.release()
//    }
//
//    private fun configDecoder() {
//        mediaExtractorModule.extract(inputPath)
//        try {
//            mediaDecoder = MediaCodec.createDecoderByType(audioMimeType)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        mediaDecoder!!.configure(audioMediaFormat, null, null, 0)
//    }
//
//    private fun configEncoder() {
//        val mediaFormat = MediaFormat.createAudioFormat(mimeType, sampleRate, channelCount)
//        mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, aacProfile)
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate)
//        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize)
////        val duration = audioMediaFormat.getLong(MediaFormat.KEY_DURATION)
////        mediaFormat.setLong(MediaFormat.KEY_DURATION, duration)
//        try {
//            mediaEncoder = MediaCodec.createEncoderByType(mimeType)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        mediaEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
//    }
//
//}