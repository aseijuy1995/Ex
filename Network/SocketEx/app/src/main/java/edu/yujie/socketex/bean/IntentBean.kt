package edu.yujie.socketex.bean

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File

//data class IntentSetting(
//    val context: Context? = null,
//    val file: File? = null,
//    val doStart: ((builder: IntentBuilder) -> Unit)? = null
//)

sealed class IntentSetting {

    data class CameraSetting(
        val context: Context,
        val file: File,
        val doStart: (builder: IntentBuilder) -> Unit
    ) : IntentSetting()

    data class CropSetting(
        val uri: Uri,
        val format: String = "JPEG",
        val scale: Boolean = true,
        val aspectX: Int = 9,
        val aspectY: Int = 16,
        val outputX: Int = 720,
        val outputY: Int = 1280,
        val return_data: Boolean = false,
        val doStart: (builder: IntentBuilder) -> Unit
    ) : IntentSetting()

    data class AlbumSetting(
        val doStart: (builder: IntentBuilder) -> Unit
    ) : IntentSetting()
}


sealed class IntentResult(
    open val requestCode: Int,
    open val resultCode: Int,
    open val data: Intent?,
    var uris: List<Uri>? = null
) {
    data class IntentResultDefault(override val requestCode: Int, override val resultCode: Int, override val data: Intent?) : IntentResult(requestCode, resultCode, data)

    data class IntentResultSuccess(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)

    data class IntentResultFailed(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)
}

data class IntentBuilder(
    val intent: Intent,
    val requestCode: Int
)