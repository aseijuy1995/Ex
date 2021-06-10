package tw.north27.coachingapp.media

import android.content.Context
import android.media.MediaRecorder
import java.io.File

data class RecorderSetting(
    val context: Context,
    val audioSource: Int = (MediaRecorder.AudioSource.MIC),
    val outputFormat: Int = MediaRecorder.OutputFormat.THREE_GPP,
    val audioEncoder: Int = MediaRecorder.AudioEncoder.AMR_NB,
    val file: File = File(context.externalCacheDir, "Audio_${System.nanoTime()}.ogg"),
){
    init {

    }
}
//val outputFormat: Int = MediaRecorder.OutputFormat.OGG,
//val audioEncoder: Int = MediaRecorder.AudioEncoder.VORBIS,

//val filePath: File? = context.externalCacheDir,
//val fileName: String = "Audio_${System.nanoTime()}.3gp"