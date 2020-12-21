package edu.yujie.retrofitex

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.yujie.okhttpex.util.OkHttpUtil
import edu.yujie.retrofitex.util.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val baseUrl = "https://my-json-server.typicode.com/"
    private val baseUrl2 = "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvView = findViewById<TextView>(R.id.tv_view)
        val btnView = findViewById<Button>(R.id.btn_view)
        val btnView2 = findViewById<Button>(R.id.btn_view2)

        var service = RetrofitManager.init(baseUrl, OkHttpUtil.get(this).client).create<IApiService>()

        btnView.setOnClickListener {
            service = RetrofitManager.changeBaseUrl(baseUrl).create<IApiService>()
            lifecycleScope.launch(Dispatchers.IO) {
                val githubBean = service.getGithubDatas()
                withContext(Dispatchers.Main) {
                    tvView.text = githubBean.toString()
                }
            }
        }

        btnView2.setOnClickListener {
            service = RetrofitManager.changeBaseUrl(baseUrl2).create<IApiService>()
            lifecycleScope.launch(Dispatchers.IO) {
                val response = service.getOtherGithubData()
                withContext(Dispatchers.Main) {
                    tvView.text = response.toString()
                }
            }
        }

    }
}