package tw.north27.coachingapp.media

import android.media.MediaCodecInfo
import android.media.MediaCrypto
import android.media.MediaFormat
import android.media.MediaMuxer
import android.view.Surface

data class CodecSetting(
    val inputPath: String,
    val outputPath: String,
    val surface: Surface? = null,
    val crypto: MediaCrypto? = null,
    //
    val mimeType: String = "video/avc",
    val width: Int = 1920,
    val height: Int = 1080,
    //原始視頻格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
    //YUV格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible

    val colorFormat: Int = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible,
    val bitRate: Int = 1024 * 100,
    val frameRate: Int = 30,
    val iFrameInterval: Int = 5,
    val format: Int = MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4,
)

data class DecodeSetting(
    val inputPath: String? = null,
    val outputPath: String? = null,
    val surface: Surface? = null,
    val crypto: MediaCrypto? = null,
)

data class EncodeSetting(
    val mimeType: String = MediaFormat.MIMETYPE_VIDEO_AVC,
    val width: Int = 1280,
    val height: Int = 720,
    //原始視頻格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface
    //YUV格式 - MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
    val colorFormat: Int = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible,
//    val bitRate: Int = 1024 * 100,
    val bitRate: Int = width * height * 6,
    val frameRate: Int = 30,
    val iFrameInterval: Int = 1,
    val format: Int = MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4,
    //
    val outputPath: String
)