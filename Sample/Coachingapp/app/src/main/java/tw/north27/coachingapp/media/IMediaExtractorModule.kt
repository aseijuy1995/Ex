package tw.north27.coachingapp.media

import android.content.Context
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import okio.IOException
import timber.log.Timber
import java.nio.ByteBuffer


interface IMediaExtractorModule {

    fun extractVideo(path: String)

}

class MediaExtractorModule(private val context: Context) : IMediaExtractorModule {

//    override fun extractFromUri(uri: Uri): Pair<MediaFormat, MediaFormat> {
//        val mediaExtractor = MediaExtractor()
//        mediaExtractor.setDataSource(context, uri, null)
//        val mediaformat = extractorMedia(mediaExtractor)
//        return mediaformat
//    }

    override fun extractVideo(path: String) {
        val mediaExtractor = MediaExtractor().also { it.setDataSource(path) }
        var extractor: MediaExtractor? = mediaExtractor
        val mediaFormatMap = mutableMapOf<String, MediaFormat?>()
        try {
            Timber.d("TrackCount =${extractor!!.trackCount}")
            for (i in 0 until extractor.trackCount) {
                val mediaFormat = extractor.getTrackFormat(i)
                val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)!!
                when {
                    mimeType.startsWith("audio/") -> mediaFormatMap[mimeType] = mediaFormat
                    mimeType.startsWith("video/") -> mediaFormatMap[mimeType] = mediaFormat
                }
                extractor.selectTrack(i)
            }
            mediaFormatMap.map {
                it.value?.let { mediaFormat ->
                    val maxInputSize = mediaFormat.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE) ?: 65536
                    Timber.d("MaxInputSize = $maxInputSize")
                    val byteBuffer = ByteBuffer.allocate(maxInputSize)
                    var sampleDataSize = 0
                    val bufferInfo = MediaCodec.BufferInfo()
                    while (true) {
                        sampleDataSize = mediaExtractor.readSampleData(byteBuffer, 0)
                        Timber.d("sampleDataSize = $sampleDataSize")
                        if (sampleDataSize < 0) break
                        bufferInfo.offset = 0
                        bufferInfo.presentationTimeUs = mediaExtractor.sampleTime
                        bufferInfo.size = sampleDataSize
                        bufferInfo.flags = mediaExtractor.sampleFlags
                        mediaExtractor.advance()
                    }
                }
            }
            Timber.d("release")
            mediaExtractor.release()
            extractor = null
        } catch (e: IOException) {
            Timber.d("IOException: ${e.message}")
            throw IOException(e.message)
        }
    }

    /**
     * pair.first；audio
     * pair.second；video
     * */
    private fun extractorMedia(mediaExtractor: MediaExtractor) {

    }
}