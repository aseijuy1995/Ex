package edu.yujie.socketex.bean

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

sealed class IntentSetting {
    data class CameraSetting(
        val context: Context,
        val filePath: File? = context.externalCacheDir,
        val fileName: String = "Image_${System.nanoTime()}.jpg"
    ) : IntentSetting()

    data class CropSetting(
        val uri: Uri,
        val format: String = "JPEG",
        val scale: Boolean = true,
        val aspectX: Int = 9,
        val aspectY: Int = 16,
        val outputX: Int = 720,
        val outputY: Int = 1280,
        val return_data: Boolean = false
    ) : IntentSetting()

    object AlbumSetting : IntentSetting()
}

data class IntentBuilder(
    val intent: Intent,
    val requestCode: Int
)

sealed class IntentResult(
    open val requestCode: Int,
    open val resultCode: Int,
    open val data: Intent?,
    var uris: List<Uri>? = null
) {
    data class IntentResultDefault(override val requestCode: Int, override val resultCode: Int, override val data: Intent?) : IntentResult(requestCode, resultCode, data)

    data class IntentResultSuccess(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)

    data class IntentResultFailed(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)

    data class IntentResultOther(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)
}