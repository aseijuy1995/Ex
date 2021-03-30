package tw.north27.coachingapp.media

import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber
import tw.north27.coachingapp.chat.MimeType
import java.io.IOException


interface IMediaCodecModule {

    /**
     * 解碼器
     * */
    fun createDecoder(filePath: String)

    /**
     * 編碼器
     * */
    fun createEncoder()


//    fun createMediaDecoder(filePath: String, encodeType: String)


}

class AudioMediaCodecModule(private val context: Context) : IMediaCodecModule {

    override fun createDecoder(filePath: String) {
        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(filePath)
        for (trackIndex in 0 until mediaExtractor.trackCount) {
            val mediaFormat = mediaExtractor.getTrackFormat(trackIndex)
            val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME) ?: throw NullPointerException("Unable to read mimeType parameter!")
            if (mimeType.startsWith(MimeType.AUDIO.toString())) {
                mediaExtractor.selectTrack(trackIndex)
                try {
                    val mediaCodec = MediaCodec.createDecoderByType(mimeType)
                    mediaCodec.setCallback(object : MediaCodec.Callback() {
                        override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
                            val byteBuffer = codec.getInputBuffer(index)
                            var sampleSize = 0
                            byteBuffer?.let {
                                sampleSize = mediaExtractor.readSampleData(it, 0)
                            }
                            if (sampleSize < 0) {
                                codec.queueInputBuffer(index, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
                            } else {
                                val sampleTime: Long = mediaExtractor.sampleTime
                                codec.queueInputBuffer(index, 0, sampleSize, sampleTime, 0)
                                mediaExtractor.advance()
                            }
                        }

                        override fun onOutputBufferAvailable(codec: MediaCodec, index: Int, info: MediaCodec.BufferInfo) {
                            codec.releaseOutputBuffer(index, true)
                        }

                        override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
                            Timber.d("onOutputFormatChanged(), format = $format")
                        }

                        override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
                            Timber.d("onError(), MediaCodec.CodecException = ${e.message}")
                        }

                    })
                    mediaCodec.configure(mediaFormat, null, null, 0)
                    mediaCodec.start()

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun createEncoder() {
        TODO("Not yet implemented")
    }

    //
    //
    //
    //
    //
//    var mediaExtractor: MediaExtractor? = null
//    var decodeInputBuffer: Array<ByteBuffer>? = null
//    var decodeOutputBuffer: Array<ByteBuffer>? = null
//    var decodeBufferInfo: MediaCodec.BufferInfo? = null
//    var mediaDecoder: MediaCodec? = null
//    var decodeSize: Int = 0
//
//    //
//    var encodeInputBuffer: Array<ByteBuffer>? = null
//    var encodeOutputBuffer: Array<ByteBuffer>? = null
//    var encodeBufferInfo: MediaCodec.BufferInfo? = null
//
//    override fun createMediaDecoder(filePath: String, encodeType: String) {
//        mediaExtractor = MediaExtractor()
//        mediaExtractor?.let { extractor ->
//            try {
//                extractor.setDataSource(filePath)
//                for (i in 0 until extractor.trackCount) {
//                    val mediaFormat = extractor.getTrackFormat(i)
//                    val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)
//                    mimeType?.let { type ->
//                        if (type.startsWith(MimeType.AUDIO.toString())) {
//                            extractor.selectTrack(i)
//                            mediaDecoder = MediaCodec.createDecoderByType(type)
//                            mediaDecoder?.let {
//                                it.configure(mediaFormat, null, null, 0)
//                                it.start()
//                                decodeInputBuffer = it.inputBuffers
//                                decodeOutputBuffer = it.outputBuffers
//                            }
//                            decodeBufferInfo = MediaCodec.BufferInfo()
//                        }
//                    } ?: throw NullPointerException("Unable to read mimeType parameter!")
//                }
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        } ?: throw NullPointerException("MediaExtractor cannot be empty!")
//
//        if (encodeType == MediaFormat.MIMETYPE_AUDIO_AAC) {
//            createMediaEncoder(encodeType)
//        } else if (encodeType == MediaFormat.MIMETYPE_AUDIO_MPEG) {
//
//        }
//
//        for (i in decodeInputBuffer!!.indices) {
//            val inputIndex = mediaDecoder!!.dequeueInputBuffer(-1)
//            if (inputIndex < 0) break
//            val inputBuffer = decodeInputBuffer!![inputIndex]
//            inputBuffer.clear()
//            val sampleSize = mediaExtractor!!.readSampleData(inputBuffer, 0)
//            if (sampleSize < 0) break
//            mediaDecoder!!.queueInputBuffer(inputIndex, 0, sampleSize, 0, 0)
//            mediaExtractor!!.advance()
//            decodeSize += sampleSize
//        }
//
//        var decodeIndex = mediaDecoder!!.dequeueOutputBuffer(decodeBufferInfo!!, 10000)
//
//        //
//        var outputBuffer: ByteBuffer? = null
//        var chunkPCM: ByteArray? = null
//        while (decodeIndex >= 0) {//每次解码完成的数据不一定能一次吐出 所以用while循环，保证解码器吐出所有数据
//            outputBuffer = decodeOutputBuffer!![decodeIndex];//拿到用于存放PCM数据的Buffer
////            chunkPCM = arrayOf();//BufferInfo内定义了此数据块的大小
//            outputBuffer.get(chunkPCM);//将Buffer内的数据取出到字节数组中
//            outputBuffer.clear();//数据取出后一定记得清空此Buffer MediaCodec是循环使用这些Buffer的，不清空下次会得到同样的数据
//            putPCMData(chunkPCM);//自己定义的方法，供编码器所在的线程获取数据,下面会贴出代码
//            mediaDecoder!!.releaseOutputBuffer(decodeIndex, false);//此操作一定要做，不然MediaCodec用完所有的Buffer后 将不能向外输出数据
//            decodeIndex = mediaDecoder!!.dequeueOutputBuffer(decodeBufferInfo!!, 10000);//再次获取数据，如果没有数据输出则outputIndex=-1 循环结束
//        }
//        //
//
//
//        while (decodeIndex > 0) {
//            val outputBuffer = decodeOutputBuffer!![decodeIndex]
//            outputBuffer.
//
//
//        }
//    }
//
//    private fun createMediaEncoder(encodeType: String) {
//        val mediaFormat = MediaFormat.createAudioFormat(encodeType, 44100, 2)
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 96000)//比特率
//        mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)//AAC
//        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 100 * 1024)
//        val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)
//        mimeType?.let { type ->
//            val mediaEncode = MediaCodec.createEncoderByType(type)
//            encodeInputBuffer = mediaEncode.inputBuffers
//            encodeOutputBuffer = mediaEncode.outputBuffers
//            encodeBufferInfo = MediaCodec.BufferInfo()
//        } ?: throw NullPointerException("Unable to read mimeType parameter!")
//
//    }
//
//    private fun extractMedia(
//        compType: MimeType,//startWith mimeType
//        type: String,
//        extractor: MediaExtractor,
//        format: MediaFormat,
//        trackIndex: Int
//    ) {
//        try {
//            if (type.startsWith(compType.toString())) {
//                extractor.selectTrack(trackIndex)
////            val maxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE)
////            Timber.d("MaxInputSize = $maxInputSize")
////            val byteBuffer = ByteBuffer.allocate(maxInputSize)
////            val bufferInfo = MediaCodec.BufferInfo()
////            var sampleSize = 0
////            while (true) {
////                sampleSize = extractor.readSampleData(byteBuffer, 0)
////                if (sampleSize < 0) break
////                bufferInfo.flags = extractor.sampleFlags
////                bufferInfo.offset = 0
////                bufferInfo.presentationTimeUs = extractor.sampleTime
////                bufferInfo.size = sampleSize
////                extractor.advance()
////            }
//                val decodeMediaCodec = MediaCodec.createDecoderByType(type)
//                decodeMediaCodec.configure(format, null, null, 0)
//
//                /**
//                 * 設定MediaFormat編碼格式
//                 * */
////            createAudioEncoderMediaCodec(type, format, trackIndex)
//
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//
////        mediaCodec.setCallback(object : MediaCodec.Callback() {
////            override fun onInputBufferAvailable(codec: MediaCodec, index: Int) {
////                //數據輸入
////                val inputBuffer = mediaCodec.getInputBuffer(index)
////                Timber.d("onInputBufferAvailable: offset = ${bufferInfo.offset}, size = ${bufferInfo.size}, presentationTimeUs = ${bufferInfo.presentationTimeUs}, flags = ${bufferInfo.flags}")
////                mediaCodec.queueInputBuffer(
////                    index,
////                    bufferInfo.offset,
////                    bufferInfo.size,
////                    bufferInfo.presentationTimeUs,
////                    bufferInfo.flags
////                )
////            }
////
////            override fun onOutputBufferAvailable(codec: MediaCodec, index: Int, info: MediaCodec.BufferInfo) {
////                //數據輸出
////                val outputBuffer = mediaCodec.getOutputBuffer(index)
////                val outputFormat = mediaCodec.getOutputFormat(index)
////                Timber.d("onOutputBufferAvailable: index = $index, presentationTimeUs = ${bufferInfo.presentationTimeUs}")
////                mediaCodec.releaseOutputBuffer(index, bufferInfo.presentationTimeUs)
////            }
////
////            override fun onOutputFormatChanged(codec: MediaCodec, format: MediaFormat) {
////                Timber.d("onOutputFormatChanged: format = $format")
////            }
////
////            override fun onError(codec: MediaCodec, e: MediaCodec.CodecException) {
////                Timber.d("onError: ${e.message}")
////            }
////        })
//
////        mediaCodec.start()
////        while (true) {
////
////        }
////        mediaCodec.stop()
////        mediaCodec.release()
//    }
//
//    /**
//     * 生成編碼器
//     * */
//    private fun createAudioEncoderMediaCodec(encodeType: String) {
//        val mediaFormat = MediaFormat.createAudioFormat(encodeType, 44100, 2)
//        mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, 96000)
//        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 100 * 1024)
//        mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
//        var mediaCodec: MediaCodec? = null
//        try {
//            mediaCodec = MediaCodec.createDecoderByType(encodeType)
//            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        if (mediaCodec == null) {
//            Timber.e(NullPointerException("Audio encoder cannot be empty!"))
//            return
//        }
//        mediaCodec.start()
//        val inputBuffer = mediaCodec.inputBuffers
//        val outputBuffer = mediaCodec.outputBuffers
//
//        val encodeInputBufferId = mediaCodec.dequeueInputBuffer(-1)
//        if (encodeInputBufferId >= 0) {
//            mediaCodec.queueInputBuffer(encodeInputBufferId, 0, 0, 0, 0)
//        }
////        MediaFormat.createVideoFormat()
//    }
//
//    /**
//     * 生成解碼器
//     * */
//    private fun createDecoderMediaCodec(type: String, format: MediaFormat, trackIndex: Int) {
////        val encoderMediaCodec = MediaCodec.createDecoderByType(type)
////        encoderMediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
////        encoderMediaCodec.start()
////        val inputBuffer = encoderMediaCodec.getInputBuffer(trackIndex)
////        val outputBuffer = encoderMediaCodec.getOutputBuffer(trackIndex)
////
////        val encodeInputBufferId = encoderMediaCodec.dequeueInputBuffer(-1)
////        if (encodeInputBufferId >= 0) {
////            encoderMediaCodec.queueInputBuffer(encodeInputBufferId, 0, 0, 0, 0)
////        }
////
////        MediaFormat.createVideoFormat()
//    }

}