package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.IOException

class MediaExtractorModule : IMediaExtractorModule {

    var mediaFormat: MediaFormat? = null

    var mimeType: String? = null

    override fun extract(path: String, mimeType: IMediaExtractorModule.MimeType): MediaExtractor? {
        val mediaExtractor = MediaExtractor()
        try {
            mediaExtractor.setDataSource(path)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        (0..mediaExtractor.trackCount).forEach { index ->
            val mediaFormat = mediaExtractor.getTrackFormat(index)
            val _mimeType = mediaFormat.getString(MediaFormat.KEY_MIME)!!
            if (_mimeType.startsWith(mimeType.toString())) {
                this.mediaFormat = mediaFormat
                this.mimeType = _mimeType
                mediaExtractor.selectTrack(index)
                return mediaExtractor
            }
        }
        return null
    }
}
