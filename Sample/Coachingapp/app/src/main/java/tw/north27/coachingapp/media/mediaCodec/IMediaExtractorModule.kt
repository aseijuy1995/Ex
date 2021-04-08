package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaExtractor

interface IMediaExtractorModule {

    enum class MimeType(private val value: String) {
        AUDIO("audio/"),
        VIDEO("video/");

        override fun toString(): String {
            return value
        }
    }

    fun extract(path: String, mimeType: MimeType): MediaExtractor?

}