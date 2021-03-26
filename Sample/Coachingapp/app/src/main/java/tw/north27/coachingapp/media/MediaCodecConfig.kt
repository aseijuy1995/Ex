package tw.north27.coachingapp.media

import android.media.MediaFormat

data class MediaCodecConfig(
    val mime: String,//媒體格式
    val format:MediaFormat? = null,


    val codesString: String,//
    val maxInputSize: Int,//
    val pixelAspectRatioWidth: Int,//
    val pixelAspectRatioHeight: Int,//
    val bitRate: Int,//
    val duration: Long,//
)