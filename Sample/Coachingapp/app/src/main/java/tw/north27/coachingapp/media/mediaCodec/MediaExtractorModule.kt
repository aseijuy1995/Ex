package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.IOException

class MediaExtractorModule : IMediaExtractorModule {

    override val mediaExtractor = MediaExtractor()

    override var audioTrackIndex: Int? = null

    override var audioMediaFormat: MediaFormat? = null

    override var audioMimeType: String? = null

    override var videoTrackIndex: Int? = null

    override var videoMediaFormat: MediaFormat? = null

    override var videoMimeType: String? = null

    override fun extract(path: String) {
        try {
            mediaExtractor.setDataSource(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        (0 until mediaExtractor.trackCount).forEach { index ->
            val mediaFormat = mediaExtractor.getTrackFormat(index)
            val mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)!!
            if (mimeType.startsWith(IMediaExtractorModule.MimeType.AUDIO.toString())) {
                audioTrackIndex = index
                audioMediaFormat = mediaFormat
                audioMimeType = mimeType
            } else if (mimeType.startsWith(IMediaExtractorModule.MimeType.VIDEO.toString())) {
                videoTrackIndex = index
                videoMediaFormat = mediaFormat
                videoMimeType = mimeType
            }
        }
    }

}
