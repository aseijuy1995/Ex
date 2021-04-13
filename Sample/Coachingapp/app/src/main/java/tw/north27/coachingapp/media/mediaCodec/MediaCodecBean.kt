package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaCodecInfo
import android.media.MediaMuxer

data class CodecSetting(
    val inputPath: String,
    val outputPath: String,
    //
    val mimeType: String = "video/avc",
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
    val bitRate: Int = width * height * 1,
//    val bitRate: Int = width * height * 3,
    val frameRate: Int = 30,//流暢度
    val iFrameInterval: Int = 1,
    val format: Int = MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
)