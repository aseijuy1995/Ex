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
    val millisecondInterval: Long = 1,//
    val context: Context,
    val filePath: File? = context.externalCacheDir,
    val fileName: String = "Audio_${System.nanoTime()}.3gp",

    val recordingTime:Long = 1,
    val recordingTimeUnit: TimeUnit = TimeUnit.MILLISECONDS

//    long period, @NonNull TimeUnit unit
)