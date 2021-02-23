package tw.north27.coachingapp.util.http

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import org.json.JSONObject
import timber.log.Timber
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.model.result.VersionResult

class UpdateAppHttpUtil(private val context: Context, private val versionResult: VersionResult) : HttpManager {

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        Timber.d("download() url = $url, path = $path, fileName = $fileName")
        val url = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncGet() url = $url, params = $params")
        val map = mapOf(
            "update" to if (versionResult.version.replace(".", "").toInt() > BuildConfig.VERSION_NAME.replace(".", "").toInt()) "Yes" else "No",
            "new_version" to versionResult.version,
            "apk_file_url" to versionResult.apkDownloadUrl,
            "update_log" to versionResult.updateLog,
            "target_size" to versionResult.targetSize,
            "new_md5" to versionResult.newMd5,
            "constraint" to versionResult.isMandatoryUpdate
        )
        val json = JSONObject(map).toString()
        callBack.onResponse(json)
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncPost() url = $url, params = $params")
    }
}