package edu.yujie.socketex.bean

import android.content.Context
import android.media.MediaRecorder
import java.io.File

data class RecorderSetting(
    val audioSource: Int = (MediaRecorder.AudioSource.MIC),
//    val outputFormat: Int = MediaRecorder.OutputFormat.THREE_GPP,
//    val outputFormat: Int = MediaRecorder.OutputFormat.AMR_WB,
    val outputFormat: Int = MediaRecorder.OutputFormat.MPEG_4,
//    val audioEncoder: Int = MediaRecorder.AudioEncoder.AMR_NB,
//    val audioEncoder: Int = MediaRecorder.AudioEncoder.AMR_WB,
    val audioEncoder: Int = MediaRecorder.AudioEncoder.AAC,
    val minimumInterval: Long = 1000,//1s
    val context: Context,
    val filePath: File? = context.externalCacheDir,
    val fileName: String = "Audio_${System.nanoTime()}.3gp"
)