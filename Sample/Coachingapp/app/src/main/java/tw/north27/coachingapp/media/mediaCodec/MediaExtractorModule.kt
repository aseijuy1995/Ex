package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.IOException

class MediaExtractorModule : IMediaExtractorModule {

    private val mediaExtractor = MediaExtractor()

    var trackIndex: Int? = null

    var mediaFormat: MediaFormat? = null

    var mimeType: String? = null

    override fun extract(path: String, mimeType: IMediaExtractorModule.MimeType): MediaExtractor {
        try {
            mediaExtractor.setDataSource(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        (0..mediaExtractor.trackCount).forEach { index ->
            val mediaFormat = mediaExtractor.getTrackFormat(index)
            val _mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)!!
            if (_mimeType.startsWith(mimeType.toString())) {
                this.trackIndex = index
                this.mediaFormat = mediaFormat
                this.mimeType = _mimeType
                return mediaExtractor
            }
        }
        throw NullPointerException("Can't find MediaExtractor!")
    }
}
