package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor
import android.media.MediaFormat
import tw.north27.coachingapp.media.DecodeSetting
import java.io.IOException

interface IDecodeMediaCodecModule {

    fun setFilePath(inputPath: String, outputPath: String): IMediaExtractorModule

//    fun extract(mimeType: String): IDecodeMediaCodecModule

    fun configDecoder(setting: DecodeSetting): IDecodeMediaCodecModule

    fun decode(): IDecodeMediaCodecModule

//    fun createEncoder(setting: EncodeSetting): IMediaCodecModule
//
//    fun encode(): IMediaCodecModule

}

interface IMediaExtractorModule {

    fun extract(): IDecodeMediaCodecModule

}

class VideoMediaExtractorModule(path: String) : IMediaExtractorModule {
    private var mediaExtractor: MediaExtractor? = null
    private var mediaFormat: MediaFormat? = null
    private var mimeType: String? = null

    override fun extract(): IDecodeMediaCodecModule {
        mediaExtractor = MediaExtractor()
        try {
            mediaExtractor!!.setDataSource()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

class VideoDecodeMediaCodecModule : IDecodeMediaCodecModule {

    private var outputPath: String? = null

    override fun setFilePath(inputPath: String, outputPath: String): IMediaExtractorModule {
        this.outputPath = outputPath
        VideoMediaExtractorModule(inputPath)
        return
    }

    override fun configDecoder(setting: DecodeSetting): IDecodeMediaCodecModule {
        TODO("Not yet implemented")
    }

    override fun decode(): IDecodeMediaCodecModule {
        TODO("Not yet implemented")
    }

}