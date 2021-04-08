package tw.north27.coachingapp.media

import android.media.MediaCodecInfo
import android.media.MediaCrypto
import android.media.MediaFormat
import android.view.Surface

data class MediaCodecBean(
    val mime: String,//媒體格式
    val format: MediaFormat? = null,


    val codesString: String,//
    val maxInputSize: Int,//
    val pixelAspectRatioWidth: Int,//
    val pixelAspectRatioHeight: Int,//
    val bitRate: Int,//
    val duration: Long,//
)

data class DecodeSetting(
    val inputPath: String? = null,
    val outputPath: String? = null,
    val surface: Surface? = null,
    val crypto: MediaCrypto? = null
)

data class EncodeSetting(
    val mimeType: String = "video/avc",
    val width: Int,
    val height: Int,
    val colorFormat: Int = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible,
    //原始視頻格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
    //YUV格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
    val bitRate: Int = 600,
    val frameRate: Int = 30,
    val iFrameInterval: Int = 1//?
)