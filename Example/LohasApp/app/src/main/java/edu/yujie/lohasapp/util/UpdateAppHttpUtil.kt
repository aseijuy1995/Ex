package edu.yujie.lohasapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.vector.update_app.HttpManager
import edu.yujie.lohasapp.BuildConfig
import edu.yujie.lohasapp.IApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class UpdateAppHttpUtil(private val context: Context, private val scope: CoroutineScope, private val service: IApiService) : HttpManager {
    private val TAG = javaClass.simpleName

    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        println("$TAG download() url = $url, path = $path, fileName = $fileName")
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        println("$TAG asyncGet() url = $url, params = $params")
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        println("$TAG asyncPost() url = $url, params = $params")
        scope.launch(Dispatchers.IO) {
            val appResult = service.postVersion()
            val isUpdate = if (appResult.result.replace(".", "").toInt() > BuildConfig.VERSION_NAME.replace(".", "").toInt()) "Yes" else "No"
            val map = mapOf(
                "update" to isUpdate,
                "new_version" to appResult.result,
                "apk_file_url" to appResult.applestoreurl,
                "update_log" to "1.工程師加班24h\n2.除掉了一些bug蟲蟲\n3.準備放假樓~",
                "target_size" to "21M",
                "constraint" to "false"
            )
            val json = JSONObject(map).toString()
            println("$TAG json = $json")
            callBack.onResponse(json)
        }
    }


}