package tw.north27.coachingapp.media.mediaCodec

import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.util.Log
import timber.log.Timber
import tw.north27.coachingapp.media.CodecSetting
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer

class VideoCompressModule private constructor(
    private val mediaExtractorModule: IMediaExtractorModule
) : IMediaCodecModule {

    private var setting: CodecSetting? = null

    private val inputPath: String
        get() = setting?.inputPath ?: throw NullPointerException("Can't find video decode input path!")

    private val outputPath: String
        get() = setting?.outputPath ?: throw NullPointerException("Can't find video encode output path!")

    private val mimeType: String
        get() = setting?.mimeType ?: throw NullPointerException("Can't find video encode mimeType!")

    private val width: Int
        get() = setting?.width ?: throw NullPointerException("Can't find video encode width!")

    private val height: Int
        get() = setting?.height ?: throw NullPointerException("Can't find video encode height!")

    private val colorFormat: Int
        get() = setting?.colorFormat ?: throw NullPointerException("Can't find video encode colorFormat!")

    private val bitRate: Int
        get() = setting?.bitRate ?: throw NullPointerException("Can't find video encode bitRate!")

    private val frameRate: Int
        get() = setting?.frameRate ?: throw NullPointerException("Can't find video encode frameRate!")

    private val iFrameInterval: Int
        get() = setting?.iFrameInterval ?: throw NullPointerException("Can't find video encode iFrameInterval!")

    private val format: Int
        get() = setting?.format ?: throw NullPointerException("Can't find video encode format!")


    //media extractor module

    private val mediaExtractor: MediaExtractor
        get() = mediaExtractorModule.mediaExtractor ?: throw NullPointerException("Can't find mediaExtractor!")

    private val videoTrackIndex: Int
        get() = mediaExtractorModule.videoTrackIndex ?: throw NullPointerException("Can't find video track index!")

    private val videoMediaFormat: MediaFormat
        get() = mediaExtractorModule.videoMediaFormat ?: throw NullPointerException("Can't find video mediaFormat!")

    private val videoMimeType: String
        get() = mediaExtractorModule.videoMimeType ?: throw NullPointerException("Can't find video mimeType!")

    //codec
    private var mediaDecoder: MediaCodec? = null

    private var mediaEncoder: MediaCodec? = null
    //

    private val decodeOutputFile: File
        get() {
            val file = File(outputPath)
            if (!file.parentFile.exists())
                file.parentFile.mkdirs()
            if (!file.exists())
                file.createNewFile()
            Timber.d("exists = ${file.exists()}, file = $outputPath")
            return file
        }

    companion object {
        fun get() = VideoCompressModule(
            MediaExtractorModule()
        )
    }

    override fun setCompress(setting: CodecSetting): IMediaCodecModule {
        this.setting = setting
        return this
    }

    override fun compress() {
        configDecoder()
        configEncoder()
        if (mediaDecoder == null) throw NullPointerException("Can't find MediaDecoder!")
        if (mediaEncoder == null) throw NullPointerException("Can't find MediaEncoder!")
        //
        compressVideo()
        //
        close()
    }

    private fun compressVideo() {
        mediaExtractor.selectTrack(videoTrackIndex)
        mediaDecoder!!.start()
        //
        val fileChannel = FileOutputStream(decodeOutputFile).channel
        val bufferInfo = MediaCodec.BufferInfo()
        //
        mediaEncoder!!.start()
        //
        var isStop = false
        while (!isStop) {
            //input
            //獲取可輸入的緩衝區索引值
            mediaDecoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
                Timber.d("decode input buffer index = $inputBufferIndex")
                val inputByteBuffer = mediaDecoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
                inputByteBuffer.clear()
                Timber.d("decode input ByteBuffer capacity = ${inputByteBuffer.capacity()}")
                val sampleSize = mediaExtractor.readSampleData(inputByteBuffer, 0)//將分離出的樣本讀出流至指定的緩衝區中
                Timber.d("decode sampleSize = $sampleSize")
                if (sampleSize >= 0) {
                    //輸入的緩衝區中若有數據，則排至codec中開始解碼
                    mediaDecoder!!.queueInputBuffer(inputBufferIndex, 0, sampleSize, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
                    mediaExtractor.advance()
                } else {
                    //輸入的緩衝區中若為空時，需發送BUFFER_FLAG_END_OF_STREAM給codec
                    mediaDecoder!!.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    mediaExtractor.unselectTrack(videoTrackIndex)
                    isStop = true
                }
            } ?: throw NullPointerException(
                "Decode" +
                        " input buffer not available"
            )
            //output
            //獲取輸出緩衝區的索引值
            val outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
            Timber.d("outputIndex = $outputBufferIndex")
            when {
                outputBufferIndex >= 0 -> {
                    val outputByteBuffer = mediaDecoder!!.getOutputBuffer(outputBufferIndex)!!
//                    try {
//                        fileChannel.write(outputByteBuffer)
//                    } catch (e: IOException) {
//                        Timber.e(e)
//                        e.printStackTrace()
//                    }
//                    //
//                    Timber.d("outputIndex-outputIndex = $outputBufferIndex")
//                    val image = mediaDecoder!!.getOutputImage(outputBufferIndex)!!
//                    val file = File("/storage/emulated/0/DCIM/Camera/JPEG/Video${System.nanoTime()}.jpeg")
//                    file.createNewFile()
//                    if (!file.exists()) file.mkdirs()
//                    Timber.d("file.exists() = ${file.exists()}")
//                    compressToJpeg(file.absolutePath, image)
                    //
                    if (mediaEncoder != null) {
                        //
                        mediaEncoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputBufferIndex ->
                            Timber.d("encode input buffer index = $inputBufferIndex")
                            val inputByteBuffer = mediaEncoder!!.getInputBuffer(inputBufferIndex)!!//獲取可輸入的緩衝區
                            inputByteBuffer.clear()
                            Timber.d("encode input ByteBuffer capacity = ${inputByteBuffer.capacity()}")
                            val byteArray = ByteArray(outputByteBuffer.limit())
                            Timber.d("outputByteBuffer.limit() = ${outputByteBuffer.limit()}, byteArray.size = ${byteArray.size}")
                            inputByteBuffer.put(byteArray)//將yuv畫面數據流至指定的輸入緩衝區中
                            mediaEncoder!!.queueInputBuffer(inputBufferIndex, 0, byteArray.size, mediaExtractor.sampleTime, mediaExtractor.sampleFlags)
                            //
                            val bufferInfo2 = MediaCodec.BufferInfo()
                            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
                            val outputBufferIndex = mediaEncoder!!.dequeueOutputBuffer(bufferInfo2, -1)
                            Timber.d("outputBufferIndex = $outputBufferIndex")
                            when {
                                outputBufferIndex >= 0 -> {
                                    val outputByteBuffer = mediaEncoder!!.getOutputBuffer(outputBufferIndex)
                                    try {
                                        fileChannel.write(outputByteBuffer)
                                    } catch (e: IOException) {
                                        Timber.e(e)
                                        e.printStackTrace()
                                    }
                                    mediaEncoder!!.releaseOutputBuffer(outputBufferIndex, false)
                                }
                                outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                                    //MediaFormat格式轉換
                                    val mediaFormat = mediaDecoder!!.outputFormat
                                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
                                }
                                outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                                    //超時
                                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
                                }
                            }
                        } ?: throw NullPointerException("Encode input buffer not available")
                    }

                    //
                    mediaDecoder!!.releaseOutputBuffer(outputBufferIndex, false)
                    //

                }
                outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    //MediaFormat格式轉換
                    val mediaFormat = mediaDecoder!!.outputFormat
                    Timber.d("INFO_OUTPUT_FORMAT_CHANGED = MediaFormat格式已更改, mediaFormat = $mediaFormat")
                }
                outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                    //超時
                    Timber.d("INFO_TRY_AGAIN_LATER = 已超時")
                }
            }
        }
        try {
            fileChannel.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun close() {
        mediaDecoder?.stop()
        mediaDecoder?.release()
        mediaEncoder?.stop()
        mediaEncoder?.release()
        mediaExtractor.release()
    }

    private fun configDecoder() {
        mediaExtractorModule.extract(inputPath)
        try {
            mediaDecoder = MediaCodec.createDecoderByType(videoMimeType)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaDecoder!!.configure(videoMediaFormat, setting?.surface, setting?.crypto, 0)
    }

    private fun configEncoder(): IMediaCodecModule {
        try {
            mediaEncoder = MediaCodec.createEncoderByType(mimeType)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val mediaFormat = MediaFormat.createVideoFormat(mimeType, width, height)
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, colorFormat)//顏色格式
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, bitRate)//bit - 比特率（以位/秒為單位）
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate)//fps - 幀速率（以幀/秒為單位）
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, iFrameInterval)//關鍵幀頻率的關鍵幀
//        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 14000000)
        mediaEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        return this
    }

    //
    //
    //
    private fun compressToJpeg(fileName: String, image: Image) {
        val outStream: FileOutputStream
        outStream = try {
            FileOutputStream(fileName)
        } catch (ioe: IOException) {
            throw RuntimeException("Unable to create output file $fileName", ioe)
        }
        val rect: Rect = image.getCropRect()
        val yuvImage = YuvImage(getDataFromImage(image, 2), ImageFormat.NV21, rect.width(), rect.height(), null)
        yuvImage.compressToJpeg(rect, 100, outStream)
    }

    private fun getDataFromImage(image: Image, colorFormat: Int): ByteArray? {
        require(!(colorFormat != 1 && colorFormat != 2)) { "only support COLOR_FormatI420 " + "and COLOR_FormatNV21" }
        if (!isImageFormatSupported(image)) {
            throw java.lang.RuntimeException("can't convert Image to byte array, format " + image.format)
        }
        val VERBOSE = true
        val crop = image.cropRect
        val format = image.format
        val width = crop.width()
        val height = crop.height()
        val planes = image.planes
        val data = ByteArray(width * height * ImageFormat.getBitsPerPixel(format) / 8)
        val rowData = ByteArray(planes[0].rowStride)
        if (VERBOSE) Log.v("TAG", "get data from " + planes.size + " planes")
        var channelOffset = 0
        var outputStride = 1
        for (i in planes.indices) {
            when (i) {
                0 -> {
                    channelOffset = 0
                    outputStride = 1
                }
                1 -> if (colorFormat == 1) {
                    channelOffset = width * height
                    outputStride = 1
                } else if (colorFormat == 2) {
                    channelOffset = width * height + 1
                    outputStride = 2
                }
                2 -> if (colorFormat == 1) {
                    channelOffset = (width * height * 1.25).toInt()
                    outputStride = 1
                } else if (colorFormat == 2) {
                    channelOffset = width * height
                    outputStride = 2
                }
            }
            val buffer: ByteBuffer = planes[i].buffer
            val rowStride = planes[i].rowStride
            val pixelStride = planes[i].pixelStride
            if (VERBOSE) {
                Log.v("TAG", "pixelStride $pixelStride")
                Log.v("TAG", "rowStride $rowStride")
                Log.v("TAG", "width $width")
                Log.v("TAG", "height $height")
                Log.v("TAG", "buffer size " + buffer.remaining())
            }
            val shift = if (i == 0) 0 else 1
            val w = width shr shift
            val h = height shr shift
            buffer.position(rowStride * (crop.top shr shift) + pixelStride * (crop.left shr shift))
            for (row in 0 until h) {
                var length: Int
                if (pixelStride == 1 && outputStride == 1) {
                    length = w
                    buffer.get(data, channelOffset, length)
                    channelOffset += length
                } else {
                    length = (w - 1) * pixelStride + 1
                    buffer.get(rowData, 0, length)
                    for (col in 0 until w) {
                        data[channelOffset] = rowData[col * pixelStride]
                        channelOffset += outputStride
                    }
                }
                if (row < h - 1) {
                    buffer.position(buffer.position() + rowStride - length)
                }
            }
            if (VERBOSE) Log.v("TAG", "Finished reading data from plane $i")
        }
        return data
    }

    private fun isImageFormatSupported(image: Image): Boolean {
        val format = image.format
        when (format) {
            ImageFormat.YUV_420_888, ImageFormat.NV21, ImageFormat.YV12 -> return true
        }
        return false
    }

}