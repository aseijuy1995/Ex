package edu.yujie.socketex.bean

import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.util.concurrent.TimeUnit

data class RecorderSetting(
    val audioSource: Int = (MediaRecorder.AudioSource.MIC),
//    val outputFormat: Int = MediaRecorder.OutputFormat.THREE_GPP,
//    val outputFormat: Int = MediaRecorder.OutputFormat.AMR_WB,
//    val outputFormat: Int = MediaRecorder.OutputFormat.MPEG_4,
    val outputFormat: Int = MediaRecorder.OutputFormat.MPEG_2_TS,
//    val audioEncoder: Int = MediaRecorder.AudioEncoder.AMR_NB,
//    val audioEncoder: Int = MediaRecorder.AudioEncoder.AMR_WB,
    val audioEncoder: Int = MediaRecorder.AudioEncoder.AAC,
    val shortLengthTimeSec: Int = 1,//最短時間
    val context: Context,
    val filePath: File? = context.externalCacheDir,
    val fileName: String = "Audio_${System.nanoTime()}.3gp"

//    long period, @NonNull TimeUnit unit
)