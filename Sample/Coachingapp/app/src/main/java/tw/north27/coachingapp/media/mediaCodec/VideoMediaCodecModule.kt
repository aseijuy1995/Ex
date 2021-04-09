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
import tw.north27.coachingapp.media.DecodeSetting
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer


class VideoMediaCodecModule private constructor() : IMediaCodecModule {

    private var setting: DecodeSetting? = null

    private val inputPath: String
        get() = setting?.inputPath ?: throw NullPointerException("Can't find InputPath!")

    private val outputPath: String
        get() = setting?.outputPath ?: throw NullPointerException("Can't find OutputPath!")

    private val videoMediaExtractorModule: MediaExtractorModule = MediaExtractorModule()

    private val trackIndex: Int
        get() = videoMediaExtractorModule.trackIndex ?: throw NullPointerException("Can't find TrackIndex!")

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

    override suspend fun decode() {
        if (mediaExtractor == null) throw NullPointerException("Can't find MediaExtractor!")
        if (mediaDecoder == null) throw NullPointerException("Can't find MediaDecoder!")
        mediaExtractor!!.selectTrack(trackIndex)
        mediaDecoder!!.start()
        //
        val bufferInfo = MediaCodec.BufferInfo()
        var offset = 0
        //
        val outputFile = File(outputPath)
        val fileChannel = FileOutputStream(outputFile).channel
        //
        var isStop = false
        while (!isStop) {
            //input
            //獲取可輸入的緩衝區索引值
            mediaDecoder!!.dequeueInputBuffer(-1).takeIf { it >= 0 }?.let { inputIndex ->
                Timber.d("inputIndex = $inputIndex")
                val inputByteBuffer = mediaDecoder!!.getInputBuffer(inputIndex)!!//獲取可輸入的緩衝區
                inputByteBuffer.clear()
                bufferInfo.offset = offset
                val sampleSize = mediaExtractor!!.readSampleData(inputByteBuffer, offset)//將分離出的樣本讀出流至指定的緩衝區中
                bufferInfo.size = sampleSize
                Timber.d("sampleSize = $sampleSize")
                if (sampleSize >= 0) {
                    //輸入的緩衝區中若有數據，則排至codec中開始解碼
                    val sampleTime = mediaExtractor!!.sampleTime
                    bufferInfo.presentationTimeUs = sampleTime
                    val sampleFlags = mediaExtractor!!.sampleFlags
                    bufferInfo.flags = sampleFlags
                    Timber.d("sampleTime = $sampleTime, sampleFlags = $sampleFlags")
                    mediaDecoder!!.queueInputBuffer(inputIndex, offset, sampleSize, sampleTime, sampleFlags)
                    mediaExtractor!!.advance()

                } else {
                    //輸入的緩衝區中若為空時，需發送BUFFER_FLAG_END_OF_STREAM給codec
                    mediaDecoder!!.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    isStop = true
                    bufferInfo.size = 0
                    bufferInfo.flags = MediaCodec.BUFFER_FLAG_END_OF_STREAM
                }
            } ?: throw NullPointerException("Input buffer not available")
            //output
            //獲取輸出緩衝區的索引值
            val outputIndex = mediaDecoder!!.dequeueOutputBuffer(bufferInfo, -1)
            //輸出緩衝區索引值若大於0，則表示即有解碼成功之數據
            Timber.d("outputIndex = $outputIndex")
            if (outputIndex >= 0) {
//                val outputByteBuffer = mediaDecoder!!.getOutputBuffer(outputIndex)
//                try {
//                    fileChannel.write(outputByteBuffer)
//                } catch (e: IOException) {
//                    Timber.e(e)
//                    e.printStackTrace()
//                }
                //
                Timber.d("outputIndex-outputIndex = $outputIndex")
                val image = mediaDecoder!!.getOutputImage(outputIndex)!!
                //
                val file = File("/storage/emulated/0/DCIM/Camera/Video${System.nanoTime()}.jpeg")
                file.createNewFile()
                if (!file.exists()) file.mkdirs()
                Timber.d("file.exists() = ${file.exists()}")
                //
                compressToJpeg(file.absolutePath, image)
                //
                //MediaFormat格式轉換
            } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
//                val mediaFormat = mediaDecoder?.getOutputFormat(outputIndex)
                val mediaFormat = mediaDecoder!!.outputFormat
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