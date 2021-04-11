//package tw.north27.coachingapp.media.mediaCodec
//
//import android.media.MediaCodec
//import android.media.MediaExtractor
//import android.media.MediaFormat
//import android.media.MediaMuxer
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Job
//import timber.log.Timber
//import tw.north27.coachingapp.media.DecodeSetting
//import tw.north27.coachingapp.media.EncodeSetting
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//import java.nio.ByteBuffer
//import kotlin.coroutines.CoroutineContext
//
//
//class VideoMediaCodecModule2 private constructor() : , CoroutineScope {
////    IMediaCodecModule
//
//    private var decodeSetting: DecodeSetting? = null
//
//    private var encodeSetting: EncodeSetting? = null
//
//    private val inputPath: String
//        get() = decodeSetting?.inputPath ?: throw NullPointerException("Can't find InputPath!")
//
//    private val outputPath: String
//        get() = decodeSetting?.outputPath ?: throw NullPointerException("Can't find OutputPath!")
//
//    private val videoMediaExtractorModule: MediaExtractorModule = MediaExtractorModule()
//
//    private val mediaExtractor: MediaExtractor
//        get() = videoMediaExtractorModule.mediaExtractor
//
//    private val audioTrackIndex: Int
//        get() = videoMediaExtractorModule.audioTrackIndex ?: throw NullPointerException("Can't find audio track index!")
//
//    private val audioMediaFormat: MediaFormat
//        get() = videoMediaExtractorModule.audioMediaFormat ?: throw NullPointerException("Can't find audio mediaFormat!")
//
//    private val audioMimeType: String
//        get() = videoMediaExtractorModule.audioMimeType ?: throw NullPointerException("Can't find audio mimeType!")
//
//    private val videoTrackIndex: Int
//        get() = videoMediaExtractorModule.videoTrackIndex ?: throw NullPointerException("Can't find video track index!")
//
//    private val videoMediaFormat: MediaFormat
//        get() = videoMediaExtractorModule.videoMediaFormat ?: throw NullPointerException("Can't find video mediaFormat!")
//
//    private val videoMimeType: String
//        get() = videoMediaExtractorModule.videoMimeType ?: throw NullPointerException("Can't find video mimeType!")
//
//    private var mediaDecoder: MediaCodec? = null
//
//    private var mediaEncoder: MediaCodec? = null
//
//    private var mediaMuxer: MediaMuxer? = null
//
//    companion object {
//        fun create(): IMediaCodecModule {
//            return VideoMediaCodecModule2()
//        }
//    }
//
//    override fun configDecoder(setting: DecodeSetting): IMediaCodecModule {
//        this.decodeSetting = setting
//        videoMediaExtractorModule.extract(inputPath)
//        try {
//            mediaDecoder = MediaCodec.createDecoderByType(videoMimeType)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        mediaDecoder!!.configure(videoMediaFormat, setting.surface, setting.crypto, 0)
//        return this
//    }
//
//    override suspend fun decode(): IMediaCodecModule {
//        if (mediaDecoder == null) throw NullPointerException("Can't find MediaDecoder!")
//        mediaExtractor.selectTrack(videoTrackIndex)
//        mediaDecoder!!.start()
//        //
//        val bufferInfo = MediaCodec.BufferInfo()
//        val offset = 0
//        //
//        val outputFile = File(outputPath)
//        val fileChannel = FileOutputStream(outputFile).channel
//        //
//        //encode
//        val outputFile2 = File(encodeSetting!!.outputPath)
//        val fileChannel2 = FileOutputStream(outputFile2).channel
//        //
//        val bufferInfo2 = MediaCodec.BufferInfo()
//        //
//        var isStop = false
//        while (!isStop) {
//            //input
//            //獲取可輸入的緩衝區索引值
//            mediaDecoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
//                Timber.d("decode input buffer index = $inputBufferIndex")
//                val inputByteBuffer = mediaDecoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
//                inputByteBuffer.clear()
//                Timber.d("decode input ByteBuffer capacity = ${inputByteBuffer.capacity()}")
//                val sampleSize = mediaExtractor.readSampleData(inputByteBuffer, 0)//將分離出的樣本讀出流至指定的緩衝區中
//                Timber.d("decode sampleSize = $sampleSize")
//                if (sampleSize >= 0) {
//                    //輸入的緩衝區中若有數據，則排至codec中開始解碼
//                    mediaDecoder!!.queueInputBuffer(inputBufferIndex, offset, sampleSize, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
//                    mediaExtractor.advance()
//                } else {
//                    //輸入的緩衝區中若為空時，需發送BUFFER_FLAG_END_OF_STREAM給codec
//                    mediaDecoder!!.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
//                    mediaExtractor.unselectTrack(videoTrackIndex)
//                    isStop = true
//                }
//            } ?: throw NullPointerException("Input buffer not available")
//            //output
//            //獲取輸出緩衝區的索引值
//            val outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
//            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
//            Timber.d("outputIndex = $outputBufferIndex")
//            when {
//                outputBufferIndex >= 0 -> {
//                    val outputByteBuffer = mediaDecoder!!.getOutputBuffer(outputBufferIndex)!!
////                    try {
////                        fileChannel.write(outputByteBuffer)
////                    } catch (e: IOException) {
////                        Timber.e(e)
////                        e.printStackTrace()
////                    }
//                    //
//                    if (mediaEncoder != null) {
//                        //
//                        mediaEncoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
//                            Timber.d("encode input buffer index = $inputBufferIndex")
//                            val inputByteBuffer = mediaEncoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
//                            inputByteBuffer.clear()
//                            Timber.d("encode input ByteBuffer capacity = ${inputByteBuffer.capacity()}")
//                            inputByteBuffer.put(outputByteBuffer)//將yuv畫面數據流至指定的輸入緩衝區中
//                            val yuvByteBufferSize = outputByteBuffer.array().size
//                            Timber.d("yuvByteBuffer.array().size = $yuvByteBufferSize")
//                            mediaEncoder!!.queueInputBuffer(inputBufferIndex, 0, yuvByteBufferSize, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
//                            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
//                            val outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo2, -1)
//                            Timber.d("outputIndex = $outputBufferIndex")
//                            val outputBuffer = mediaEncoder!!.getOutputBuffer(outputBufferIndex)
//                            //
//                            try {
//                                fileChannel2.write(outputByteBuffer)
//                            } catch (e: IOException) {
//                                Timber.e(e)
//                                e.printStackTrace()
//                            }
//                            //
//                            mediaEncoder!!.releaseOutputBuffer(outputBufferIndex, false)
//                        }
//                    }
//
//                    //
//                    //                    Timber.d("outputIndex-outputIndex = $outputBufferIndex")
//                    //                    val image = mediaDecoder!!.getOutputImage(outputBufferIndex)!!
//                    //                    val file = File("/storage/emulated/0/DCIM/Camera/JPEG/Video${System.nanoTime()}.jpeg")
//                    //                    file.createNewFile()
//                    //                    if (!file.exists()) file.mkdirs()
//                    //                    Timber.d("file.exists() = ${file.exists()}")
//                    //                    compressToJpeg(file.absolutePath, image)
//                    //
//
//                    //
//                    mediaDecoder!!.releaseOutputBuffer(outputBufferIndex, false)
//                    //
//
//                }
//                outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
//                    //MediaFormat格式轉換
//                    val mediaFormat = mediaDecoder!!.outputFormat
//                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
//                }
//                outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
//                    //超時
//                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
//                }
//            }
//        }
//        try {
//            fileChannel.close()
//            fileChannel2.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        mediaDecoder?.stop()
//        mediaDecoder?.release()
//        mediaEncoder?.stop()
//        mediaEncoder?.release()
//        mediaExtractor.release()
//        return this
//    }
//
//    override fun configEncoder(setting: EncodeSetting): IMediaCodecModule {
//        this.encodeSetting = setting
//        try {
//            mediaEncoder = MediaCodec.createEncoderByType(setting.mimeType)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        val mediaFormat = MediaFormat.createVideoFormat(setting.mimeType, setting.width, setting.height)
//        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, setting.colorFormat)//顏色格式
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, setting.bitRate)//bit - 比特率（以位/秒為單位）
//        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, setting.frameRate)//fps - 幀速率（以幀/秒為單位）
//        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, setting.iFrameInterval)//關鍵幀頻率的關鍵幀
//        mediaEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
//        return this
//    }
//
//    override fun startMuxer(): IMediaCodecModule {
//        if (encodeSetting == null) throw NullPointerException("Can't find Encode Setting!")
//        mediaMuxer = MediaMuxer(encodeSetting!!.outputPath, encodeSetting!!.format)
//        mediaMuxer!!.addTrack(videoMediaFormat)
//        mediaMuxer!!.start()
////        mediaMuxer?.writeSampleData(videoTrackIndex, outputByteBuffer, bufferInfo)
//        return this
//    }
//
//    override suspend fun encodeYuvToAvc(yuvByteBuffer: ByteBuffer): IMediaCodecModule {
////        if (mediaEncoder == null) throw NullPointerException("Can't find MediaEncoder!")
////        val outputFile = File(encodeSetting!!.outputPath)
////        val fileChannel = FileOutputStream(outputFile).channel
////        //
////        val bufferInfo = MediaCodec.BufferInfo()
////        //
////        mediaEncoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
////            Timber.d("encode input buffer index = $inputBufferIndex")
////            val inputByteBuffer = mediaEncoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
////            inputByteBuffer.clear()
////            Timber.d("encode input ByteBuffer capacity = ${inputByteBuffer.capacity()}")
////            inputByteBuffer.put(yuvByteBuffer)//將yuv畫面數據流至指定的輸入緩衝區中
////            val yuvByteBufferSize = yuvByteBuffer.array().size
////            Timber.d("yuvByteBuffer.array().size = $yuvByteBufferSize")
////            mediaEncoder!!.queueInputBuffer(inputBufferIndex, 0, yuvByteBufferSize, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
////            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
////            val outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
////            Timber.d("outputIndex = $outputBufferIndex")
////            val outputBuffer = mediaEncoder!!.getOutputBuffer(outputBufferIndex)
////            //
////            try {
////                fileChannel.write(outputByteBuffer)
////            } catch (e: IOException) {
////                Timber.e(e)
////                e.printStackTrace()
////            }
////            //
////
////
////            mediaEncoder!!.releaseOutputBuffer(outputBufferIndex, false)
////        }
////        try {
////            fileChannel.close()
////        } catch (e: IOException) {
////            e.printStackTrace()
////        }
//
//
//        return this
//    }
//
//    override val coroutineContext: CoroutineContext
//        get() = Job()
//
////    private fun compressToJpeg(fileName: String, image: Image) {
////        val outStream: FileOutputStream
////        outStream = try {
////            FileOutputStream(fileName)
////        } catch (ioe: IOException) {
////            throw RuntimeException("Unable to create output file $fileName", ioe)
////        }
////        val rect: Rect = image.getCropRect()
////        val yuvImage = YuvImage(getDataFromImage(image, 2), ImageFormat.NV21, rect.width(), rect.height(), null)
////        yuvImage.compressToJpeg(rect, 100, outStream)
////    }
//
////    private fun getDataFromImage(image: Image, colorFormat: Int): ByteArray? {
////        require(!(colorFormat != 1 && colorFormat != 2)) { "only support COLOR_FormatI420 " + "and COLOR_FormatNV21" }
////        if (!isImageFormatSupported(image)) {
////            throw java.lang.RuntimeException("can't convert Image to byte array, format " + image.format)
////        }
////        val VERBOSE = true
////        val crop = image.cropRect
////        val format = image.format
////        val width = crop.width()
////        val height = crop.height()
////        val planes = image.planes
////        val data = ByteArray(width * height * ImageFormat.getBitsPerPixel(format) / 8)
////        val rowData = ByteArray(planes[0].rowStride)
////        if (VERBOSE) Log.v("TAG", "get data from " + planes.size + " planes")
////        var channelOffset = 0
////        var outputStride = 1
////        for (i in planes.indices) {
////            when (i) {
////                0 -> {
////                    channelOffset = 0
////                    outputStride = 1
////                }
////                1 -> if (colorFormat == 1) {
////                    channelOffset = width * height
////                    outputStride = 1
////                } else if (colorFormat == 2) {
////                    channelOffset = width * height + 1
////                    outputStride = 2
////                }
////                2 -> if (colorFormat == 1) {
////                    channelOffset = (width * height * 1.25).toInt()
////                    outputStride = 1
////                } else if (colorFormat == 2) {
////                    channelOffset = width * height
////                    outputStride = 2
////                }
////            }
////            val buffer: ByteBuffer = planes[i].buffer
////            val rowStride = planes[i].rowStride
////            val pixelStride = planes[i].pixelStride
////            if (VERBOSE) {
////                Log.v("TAG", "pixelStride $pixelStride")
////                Log.v("TAG", "rowStride $rowStride")
////                Log.v("TAG", "width $width")
////                Log.v("TAG", "height $height")
////                Log.v("TAG", "buffer size " + buffer.remaining())
////            }
////            val shift = if (i == 0) 0 else 1
////            val w = width shr shift
////            val h = height shr shift
////            buffer.position(rowStride * (crop.top shr shift) + pixelStride * (crop.left shr shift))
////            for (row in 0 until h) {
////                var length: Int
////                if (pixelStride == 1 && outputStride == 1) {
////                    length = w
////                    buffer.get(data, channelOffset, length)
////                    channelOffset += length
////                } else {
////                    length = (w - 1) * pixelStride + 1
////                    buffer.get(rowData, 0, length)
////                    for (col in 0 until w) {
////                        data[channelOffset] = rowData[col * pixelStride]
////                        channelOffset += outputStride
////                    }
////                }
////                if (row < h - 1) {
////                    buffer.position(buffer.position() + rowStride - length)
////                }
////            }
////            if (VERBOSE) Log.v("TAG", "Finished reading data from plane $i")
////        }
////        return data
////    }
////
////    private fun isImageFormatSupported(image: Image): Boolean {
////        val format = image.format
////        when (format) {
////            ImageFormat.YUV_420_888, ImageFormat.NV21, ImageFormat.YV12 -> return true
////        }
////        return false
////    }
//
//}