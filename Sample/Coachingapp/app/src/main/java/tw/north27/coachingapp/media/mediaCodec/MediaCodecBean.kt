package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaCodecInfo
import android.media.MediaMuxer

data class CodecSetting(
    val inputPath: String,
    val outputPath: String,
    //
//    val mimeType: String = "video/avc",
    val mimeType: String = "audio/mp4a-latm",
    val width: Int = 1920,//分辨率
    val height: Int = 1080,
    /**
     * Y - 明亮度
     * U - 色度（色彩及飽和度）
     * V - 色度（色彩及飽和度）
     * PS.YUV420為大部分編碼器有支援之轉換格式
     * */
    val colorFormat: Int = MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible,
    /**
     * bit - 比特率（以位/秒為單位）
     * 影響影片顯示質量
     * */
//    val bitRate: Int = width * height * 1,
    val bitRate: Int = 1024 * 100,
    val frameRate: Int = 30,//流暢度
    val iFrameInterval: Int = 1,
    val format: Int = MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4,
    //audio
    val aacProfile: Int = MediaCodecInfo.CodecProfileLevel.AACObjectLC,
    val sampleRate: Int = 44100,
    val channelCount: Int = 2,
    val channelMask: Int = 1,
    val maxInputSize: Int = 8192 * 2
)