package tw.north27.coachingapp.media

import android.content.Context
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import tw.north27.coachingapp.chat.MimeType
import java.io.*
import java.nio.ByteBuffer
import kotlin.coroutines.CoroutineContext


interface IMediaCodecModule {

    /**
     * 解碼器
     * */
    suspend fun createDecoder(filePath: String)

    /**
     * 編碼器
     * */
    fun createEncoder()

    fun encodeData(data: ByteArray)

    fun startEncode(srcPath: String)


//    fun createMediaDecoder(filePath: String, encodeType: String)

}

class AudioMediaCodecModule(private val context: Context) : IMediaCodecModule, CoroutineScope {

    override suspend fun createDecoder(filePath: String) {
        val mediaExtractor = MediaExtractor()
        for (trackIndex in 0 until mediaExtractor.trackCount) {
            val mediaFormat = mediaExtractor.getTrackFormat(trackIndex)
            val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME) ?: throw NullPointerException("Unable to read mimeType parameter!")
            if (mimeType.startsWith(MimeType.AUDIO.toString())) {
                mediaExtractor.selectTrack(trackIndex)
                var mediaCodec: MediaCodec? = null
                try {
                    mediaCodec = MediaCodec.createDecoderByType(mimeType)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                coroutineScope {
                    mediaCodec?.let { mediaCodec ->
                        mediaCodec.configure(mediaFormat, null, null, 0)
                        mediaCodec.start()
                        val bufferInfo = MediaCodec.BufferInfo()
                        val inputBufferIndex = mediaCodec.dequeueInputBuffer(-1)
                        if (inputBufferIndex >= 0) {
                            val byteBuffer = mediaCodec.getInputBuffer(inputBufferIndex) ?: throw NullPointerException("Input buffer cannot be empty!")
                            val sampleSize = mediaExtractor.readSampleData(byteBuffer, 0)
                            if (sampleSize < 0) {
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                            } else {
                                val sampleTime = mediaExtractor.sampleTime
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, sampleSize, sampleTime, 0)
                                mediaExtractor.advance()
                            }
                        }
                        //
                        val outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, -1)
                        Timber.d("outputBufferIndex = $outputBufferIndex")
                        when {
                            outputBufferIndex >= 0 -> {
                                val mediaFormat = mediaCodec.getOutputFormat(outputBufferIndex)
                                val outputBuffer = mediaCodec.getOutputBuffer(outputBufferIndex)
                                mediaCodec.releaseOutputBuffer(outputBufferIndex, true)
                            }
                            outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                                Timber.d("MediaCodec.INFO_OUTPUT_FORMAT_CHANGED = MediaFormat更改...")

                            }
                            outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                                Timber.d("MediaCodec.INFO_TRY_AGAIN_LATER = 超時...")
                            }
                        }
                    }
                }
            }
        }

    }

    var mediaEncoder: MediaCodec? = null//編碼器
    var encodeInputBuffer: Array<ByteBuffer>? = null
    var encodeOutputBuffers: Array<ByteBuffer>? = null
    var encodeBufferInfo: MediaCodec.BufferInfo? = null

    override fun createEncoder() {
        try {
            val mediaFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, 44100, 2)
            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000)//比特率
            mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
            mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 100 * 1024)
            mediaEncoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
            mediaEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaEncoder?.let {
            it.start()
            encodeInputBuffer = it.inputBuffers
            encodeOutputBuffers = it.outputBuffers
            encodeBufferInfo = MediaCodec.BufferInfo()
        }
    }

    override fun encodeData(data: ByteArray) {
//        val file = FileOutputStream(File(srcPath))
//        val bos: ByteArrayOutputStream = ByteArrayOutputStream()
//
//        if (mediaEncoder == null)
//            createEncoder()
//        val encodeInputBuffer = encodeInputBuffer!!
//        val encodeOutputBuffers = encodeOutputBuffers!!
//        val encodeBufferInfo = encodeBufferInfo!!
//
//        mediaEncoder?.let {
//            val inputBufferIndex = it.dequeueInputBuffer(-1)
//            if (inputBufferIndex >= 0) {
//                val byteBuffer = encodeInputBuffer[inputBufferIndex]
//                byteBuffer.clear()
//                byteBuffer.put(data)
//                byteBuffer.limit(data.size)
//                it.queueInputBuffer(inputBufferIndex, 0, data.size, 0, 0)
//                //
//                val outputBufferIndex = it.dequeueOutputBuffer(encodeBufferInfo, 0)
//                while (outputBufferIndex >= 0) {
//                    val bufferInfoSize = encodeBufferInfo.size
//                    val bufferInfoPacketSize = bufferInfoSize + 7
//                    val byteBuffer = encodeOutputBuffers[outputBufferIndex]
//                    byteBuffer.position(encodeBufferInfo.offset)
//                    byteBuffer.limit(encodeBufferInfo.offset + encodeBufferInfo.size)
//                    //
//                    val byteArray = ByteArray(bufferInfoPacketSize)
//                    byteBuffer.get(byteArray, 7, bufferInfoSize)
//                    byteBuffer.position(encodeBufferInfo.offset)
//                }
//            }
//        }
    }

    override fun startEncode(srcPath: String) {
        try {
            val file = File(srcPath)
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream()
            val b = ByteArray(1024)
            var n: Int
            while (fis.read(b).also { n = it } != -1) {
                bos.write(b, 0, n)
                encodeData(bos.toByteArray())
                bos.reset()
            }
            fis.close()
            bos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override val coroutineContext: CoroutineContext
        get() = Job()

}