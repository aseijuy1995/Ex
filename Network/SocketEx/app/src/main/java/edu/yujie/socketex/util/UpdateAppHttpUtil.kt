package edu.yujie.socketex.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import edu.yujie.socketex.bean.InitSettingBean
import org.json.JSONObject

class UpdateAppHttpUtil(private val context: Context, private val initSettingBean: InitSettingBean) : HttpManager {

    private val TAG = javaClass.simpleName

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        println("$TAG download() url = $url, path = $path, fileName = $fileName")
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(initSettingBean.apkFileUrl)))
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        println("$TAG asyncGet() url = $url, params = $params")
        val map = mapOf(
            "update" to if (initSettingBean.isUpdate) "Yes" else "No",
            "new_version" to initSettingBean.newVersion,
            "apk_file_url" to initSettingBean.apkFileUrl,
            "update_log" to initSettingBean.updateLog,
            "target_size" to initSettingBean.targetSize,
            "new_md5" to initSettingBean.newMd5,
            "constraint" to initSettingBean.isMandatoryUpdate
        )
        val json = JSONObject(map).toString()
        callBack.onResponse(json)
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        println("$TAG asyncPost() url = $url, params = $params")
    }


}