package tw.north27.coachingapp.media

import android.media.MediaExtractor
import android.media.MediaFormat

interface IMediaExtractorModule {
}

class MediaExtractorModule : IMediaExtractorModule {


    fun set(videoPath: String) {

        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(videoPath)
        var videoTrackIndex = -1
        for (i in 0..mediaExtractor.trackCount) {
            val format = mediaExtractor.getTrackFormat(i)
            format.getString(MediaFormat.KEY_MIME)?.let {
                if (it.startsWith("video/")) {
                    mediaExtractor.selectTrack(i)
                }
            }


        }


    }
}