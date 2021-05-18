package tw.north27.coachingapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import org.json.JSONObject
import timber.log.Timber
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.model.result.UpdateInfo

class UpdateHttpManager(private val cxt: Context, private val updateInfo: UpdateInfo) : HttpManager {

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        Timber.i("url = $url, path = $path, fileName = $fileName")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        }
        cxt.startActivity(intent)
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.i("url = $url, params = $params")
        val newVersion = updateInfo.versionName.replace(".", "").toInt()
        val currentVersion = BuildConfig.VERSION_NAME.replace(".", "").toInt()
        val update = if (newVersion > currentVersion) "Yes" else "No"
        val map = mapOf(
            "update" to update,
            "new_version" to updateInfo.versionName,
            "apk_file_url" to updateInfo.url,
            "update_log" to updateInfo.text,
            "target_size" to updateInfo.size,
//            "new_md5" to updateInfo.md5,
            "new_md5" to "",
            "constraint" to updateInfo.isMandatory
        )
        val json = JSONObject(map).toString()
        callBack.onResponse(json)
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("url = $url, params = $params")
    }

}