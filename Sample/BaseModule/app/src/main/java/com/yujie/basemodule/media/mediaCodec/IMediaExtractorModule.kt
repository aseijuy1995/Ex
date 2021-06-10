package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor
import android.media.MediaFormat

interface IMediaExtractorModule {

    val mediaExtractor: MediaExtractor

    var audioTrackIndex: Int?

    var audioMediaFormat: MediaFormat?

    var audioMimeType: String?

    var videoTrackIndex: Int?

    var videoMediaFormat: MediaFormat?

    var videoMimeType: String?

    enum class MimeType(private val value: String) {
        AUDIO("audio/"),
        VIDEO("video/");

        override fun toString(): String {
            return value
        }
    }

    fun extract(path: String)

}