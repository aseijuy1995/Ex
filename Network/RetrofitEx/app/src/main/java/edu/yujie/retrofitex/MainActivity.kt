package edu.yujie.retrofitex

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import edu.yujie.retrofitex.util.OkHttpUtil
import edu.yujie.retrofitex.util.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val baseUrl = "https://my-json-server.typicode.com/"
    private val baseUrl2 = "https://jsonplaceholder.typicode.com/"
    private var manager = RetrofitManager.init(baseUrl, OkHttpUtil.get(this).client)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvView = findViewById<TextView>(R.id.tv_view)
        val btnView = findViewById<Button>(R.id.btn_view)
        val btnView2 = findViewById<Button>(R.id.btn_view2)

        btnView.setOnClickListener {
            manager.changeBaseUrl(baseUrl)
            val apiService = manager.create<IApiService>()
            lifecycleScope.launch(Dispatchers.IO) {
                val githubBean = apiService.getGithubDatas()
                withContext(Dispatchers.Main) {
                    tvView.text = githubBean.toString()
                }
            }
        }

        btnView2.setOnClickListener {
            manager.changeBaseUrl(baseUrl2)
            val apiService2 = manager.create<IApiService2>()
            lifecycleScope.launch(Dispatchers.IO) {
                val response = apiService2.getOtherGithubData()
                withContext(Dispatchers.Main) {
                    tvView.text = response.toString()
                }
            }
        }

    }
}