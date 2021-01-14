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
        val file: File
    ) : IntentSetting()

    object AlbumSetting : IntentSetting()
}


open class IntentResult(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent?,
    var uris: List<Uri>? = null
) {
    data class IntentResultSuccess(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)

    data class IntentResultFailed(val result: IntentResult) : IntentResult(result.requestCode, result.resultCode, result.data, result.uris)
}

data class IntentBuilder(
    val intent: Intent,
    val requestCode: Int
)