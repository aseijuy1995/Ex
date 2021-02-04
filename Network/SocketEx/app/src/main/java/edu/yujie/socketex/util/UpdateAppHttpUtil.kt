package edu.yujie.socketex.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import edu.yujie.socketex.BuildConfig
import edu.yujie.socketex.finish.bean.InitBean
import org.json.JSONObject
import timber.log.Timber

class UpdateAppHttpUtil(private val context: Context, private val initBean: InitBean) : HttpManager {

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        Timber.d("download() url = $url, path = $path, fileName = $fileName")
        val url = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncGet() url = $url, params = $params")
        val map = mapOf(
            "update" to if (initBean.version.replace(".", "").toInt() > BuildConfig.VERSION_NAME.replace(".", "").toInt()) "Yes" else "No",
            "new_version" to initBean.version,
            "apk_file_url" to initBean.apkFileUrl,
            "update_log" to initBean.updateLog,
            "target_size" to initBean.targetSize,
            "new_md5" to initBean.newMd5,
            "constraint" to initBean.isMandatoryUpdate
        )
        val json = JSONObject(map).toString()
        callBack.onResponse(json)
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        Timber.d("asyncPost() url = $url, params = $params")
    }
}