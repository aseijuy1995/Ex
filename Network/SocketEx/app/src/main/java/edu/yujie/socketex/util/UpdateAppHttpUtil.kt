package edu.yujie.socketex.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import edu.yujie.socketex.BuildConfig
import edu.yujie.socketex.finish.result.InitResult
import org.json.JSONObject
import timber.log.Timber

class UpdateAppHttpUtil(private val context: Context, private val initResult: InitResult) : HttpManager {

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        Timber.d("download() url = $url, path = $path, fileName = $fileName")
        val url = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncGet() url = $url, params = $params")
        val map = mapOf(
            "update" to if (initResult.version.replace(".", "").toInt() > BuildConfig.VERSION_NAME.replace(".", "").toInt()) "Yes" else "No",
            "new_version" to initResult.version,
            "apk_file_url" to initResult.apkFileUrl,
            "update_log" to initResult.updateLog,
            "target_size" to initResult.targetSize,
            "new_md5" to initResult.newMd5,
            "constraint" to initResult.isMandatoryUpdate
        )
        val json = JSONObject(map).toString()
        callBack.onResponse(json)
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncPost() url = $url, params = $params")
    }
}