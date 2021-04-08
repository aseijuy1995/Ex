package tw.north27.coachingapp.media

import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import tw.north27.coachingapp.media.mediaStore.MimeType
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer


interface IAudioMediaCodecModule {

    /**
     * 輸入Source Path
     * */
    fun addSource(path: String): IAudioMediaCodecModule

    /**
     * 輸出PCM Path
     * */
    fun outputPcm(path: String): IAudioMediaCodecModule

    /**
     * 解碼器
     * */
    fun createDecoder(): IAudioMediaCodecModule

    /**
     * 開始解碼
     * */
    suspend fun decodeData()


//    fun createMediaDecoder(filePath: String, encodeType: String)

}

class AudioMediaCodecModule(private val context: Context) : IAudioMediaCodecModule {
    var sourcePath: String? = null
    var pcmPath: String? = null
    var mediaExtractor: MediaExtractor? = null
    var mediaFormat: MediaFormat? = null
    var trackIndex: Int? = null
    var mediaDecoder: MediaCodec? = null

    var decodeBufferInfo: MediaCodec.BufferInfo? = null

    override fun addSource(path: String): IAudioMediaCodecModule {
        val file = File(path)
        if (!file.exists()) throw FileNotFoundException("Can't find add source path!")
        Timber.d("addSource Path = ${file.absolutePath}")
        this.sourcePath = file.absolutePath
        return this
    }

    override fun outputPcm(path: String): IAudioMediaCodecModule {
        val file = File(path)
        if (file.exists()) file.delete()
        file.parentFile?.mkdirs()
        try {
            file.createNewFile()
        } catch (e: IOException) {
            Timber.e(e)
            e.printStackTrace()
        }
        Timber.d("file.absolutePath - ${file.absolutePath}")
        pcmPath = file.absolutePath
        return this
    }

    override fun createDecoder(): IAudioMediaCodecModule {
        if (sourcePath == null) throw FileNotFoundException("Can't find sourcePath!")
        mediaExtractor = MediaExtractor()
        try {
            mediaExtractor!!.setDataSource(sourcePath!!)
        } catch (e: IOException) {
            Timber.e(e)
            e.printStackTrace()
        }
        for (trackIndex in 0 until mediaExtractor!!.trackCount) {
            mediaFormat = mediaExtractor!!.getTrackFormat(trackIndex)
//            mediaFormat!!.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 1024 * 100)
            val mimeType = mediaFormat!!.getString(MediaFormat.KEY_MIME) ?: throw NullPointerException("Unable to read mimeType parameter!")
            Timber.d("mediaFormat = $mediaFormat, mimeType = $mimeType")
            if (mimeType.startsWith(MimeType.AUDIO.toString())) {
                this.trackIndex = trackIndex
                try {
                    mediaDecoder = MediaCodec.createDecoderByType(mimeType)
                } catch (e: IOException) {
                    Timber.e(e)
                    e.printStackTrace()
                }
                mediaDecoder!!.configure(mediaFormat, null, null, 0)
            }
        }
        return this
    }

    override suspend fun decodeData() = coroutineScope {
        if (sourcePath == null) throw FileNotFoundException("Can't find sourcePath!")
        if (pcmPath == null) {
            val file = File(sourcePath!!)
            val filePath = file.parentFile
            var fileName = sourcePath!!.substring(sourcePath!!.lastIndexOf("/") + 1)
            val index = fileName.indexOf(".")
            val name = fileName.substring(0, index)
            fileName = "${name}_2"
            pcmPath = "${filePath}${fileName}.pcm"
            Timber.d("pcmPath = $pcmPath")
        }
        if (mediaExtractor == null) throw NullPointerException("MediaExtractor has been created!")
        if (mediaFormat == null) throw NullPointerException("Can't find MediaFormat!")
        if (trackIndex == null) throw NullPointerException("Can't find track index!")
        if (mediaDecoder == null) throw NullPointerException("MediaCodec Decoder has been created!")
        //
        decodeBufferInfo = MediaCodec.BufferInfo()
        var maxInputSize: Int = 100 * 1024
        if (mediaFormat!!.containsKey(MediaFormat.KEY_MAX_INPUT_SIZE)) maxInputSize = mediaFormat!!.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
        val maxByteBuffer = ByteBuffer.allocateDirect(maxInputSize)
        //
        mediaDecoder!!.start()
        mediaExtractor!!.selectTrack(trackIndex!!)
        /**
         * Channel是對I/O操作的封裝。FileChannel配合著ByteBuffer，FileChannel配合著ByteBuffer，將讀寫的資料快取到記憶體中，然後以批量/快取的方式read/write，去了非批量操作時的重複中間操作，操縱大檔案時可以顯著提高效率
         * */
        /**
         * Channel是對I/O操作的封裝。FileChannel配合著ByteBuffer，FileChannel配合著ByteBuffer，將讀寫的資料快取到記憶體中，然後以批量/快取的方式read/write，去了非批量操作時的重複中間操作，操縱大檔案時可以顯著提高效率
         * */
        val fileChannel = FileOutputStream(File(pcmPath)).channel
        //
        var sampleSize: Int = 0
        while (mediaExtractor!!.readSampleData(maxByteBuffer, 0).also { sampleSize = it } >= 0) {
            val inputByteBufferIndex = mediaDecoder!!.dequeueInputBuffer(-1)
//            val inputByteBuffer = inputByteBuffers[inputByteBufferIndex]
            Timber.d("sampleSize = $sampleSize")
            if (sampleSize < 0) {
                mediaDecoder!!.queueInputBuffer(inputByteBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
            } else {
                decodeBufferInfo!!.size = mediaExtractor!!.readSampleData(maxByteBuffer, 0);
                decodeBufferInfo!!.presentationTimeUs = mediaExtractor!!.sampleTime
                decodeBufferInfo!!.flags = mediaExtractor!!.sampleFlags
                try {
                    fileChannel.write(maxByteBuffer, maxByteBuffer.remaining().toLong())
                } catch (e: IOException) {
                    Timber.e(e)
                    e.printStackTrace()
                }
                //把實際的remaining 壓縮資料給到dsp解碼
                val byteArray = ByteArray(maxByteBuffer.remaining())
                maxByteBuffer.get(byteArray)

                val inputByteBuffer = mediaDecoder!!.getInputBuffer(inputByteBufferIndex)
                inputByteBuffer!!.put(byteArray)

                mediaDecoder!!.queueInputBuffer(inputByteBufferIndex, 0, decodeBufferInfo!!.size, decodeBufferInfo!!.presentationTimeUs, 0)
                mediaExtractor!!.advance()
            }
            var outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(decodeBufferInfo!!, -1)
            Timber.d("outputBufferIndex = $outputBufferIndex")
            when {
                outputBufferIndex >= 0 -> {
                    val outputByteBuffer = mediaDecoder!!.getOutputBuffer(outputBufferIndex)
                    try {
                        fileChannel.write(outputByteBuffer)
                    } catch (e: IOException) {
                        Timber.e(e)
                        e.printStackTrace()
                    }
                    mediaDecoder!!.releaseOutputBuffer(outputBufferIndex, false)
                    outputBufferIndex = mediaDecoder!!.dequeueOutputBuffer(decodeBufferInfo!!, -1)
                }
                outputBufferIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    Timber.d("MediaCodec.INFO_OUTPUT_FORMAT_CHANGED = MediaFormat更改...")
                    val mediaFormat = mediaDecoder!!.outputFormat

                }
                outputBufferIndex == MediaCodec.INFO_TRY_AGAIN_LATER -> {
                    Timber.d("MediaCodec.INFO_TRY_AGAIN_LATER = 超時...")
                }
            }
        }
        try {
            fileChannel.close()
        } catch (e: IOException) {
            Timber.e(e)
            e.printStackTrace()
        }
        mediaDecoder!!.stop()
        mediaDecoder!!.release()
        mediaExtractor!!.release()

    }

//    var mediaEncoder: MediaCodec? = null//編碼器
//    var encodeInputBuffer: Array<ByteBuffer>? = null
//    var encodeOutputBuffers: Array<ByteBuffer>? = null
//    var encodeBufferInfo: MediaCodec.BufferInfo? = null

//    override fun createEncoder() {
//        try {
//            val mediaFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, 44100, 2)
//            mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 64000)//比特率
//            mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
//            mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 100 * 1024)
//            mediaEncoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
//            mediaEncoder!!.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        mediaEncoder?.let {
//            it.start()
//            encodeInputBuffer = it.inputBuffers
//            encodeOutputBuffers = it.outputBuffers
//            encodeBufferInfo = MediaCodec.BufferInfo()
//        }
//    }
//
//    override fun encodeData(data: ByteArray) {
////        val file = FileOutputStream(File(srcPath))
////        val bos: ByteArrayOutputStream = ByteArrayOutputStream()
////
////        if (mediaEncoder == null)
////            createEncoder()
////        val encodeInputBuffer = encodeInputBuffer!!
////        val encodeOutputBuffers = encodeOutputBuffers!!
////        val encodeBufferInfo = encodeBufferInfo!!
////
////        mediaEncoder?.let {
////            val inputBufferIndex = it.dequeueInputBuffer(-1)
////            if (inputBufferIndex >= 0) {
////                val byteBuffer = encodeInputBuffer[inputBufferIndex]
////                byteBuffer.clear()
////                byteBuffer.put(data)
////                byteBuffer.limit(data.size)
////                it.queueInputBuffer(inputBufferIndex, 0, data.size, 0, 0)
////                //
////                val outputBufferIndex = it.dequeueOutputBuffer(encodeBufferInfo, 0)
////                while (outputBufferIndex >= 0) {
////                    val bufferInfoSize = encodeBufferInfo.size
////                    val bufferInfoPacketSize = bufferInfoSize + 7
////                    val byteBuffer = encodeOutputBuffers[outputBufferIndex]
////                    byteBuffer.position(encodeBufferInfo.offset)
////                    byteBuffer.limit(encodeBufferInfo.offset + encodeBufferInfo.size)
////                    //
////                    val byteArray = ByteArray(bufferInfoPacketSize)
////                    byteBuffer.get(byteArray, 7, bufferInfoSize)
////                    byteBuffer.position(encodeBufferInfo.offset)
////                }
////            }
////        }
//    }
//
//    override fun startEncode(srcPath: String) {
//        try {
//            val file = File(srcPath)
//            val fis = FileInputStream(file)
//            val bos = ByteArrayOutputStream()
//            val b = ByteArray(1024)
//            var n: Int
//            while (fis.read(b).also { n = it } != -1) {
//                bos.write(b, 0, n)
//                encodeData(bos.toByteArray())
//                bos.reset()
//            }
//            fis.close()
//            bos.close()
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

}