package edu.yujie.retrofitex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import edu.yujie.retrofitex.databinding.ActivityRetrofitBinding
import edu.yujie.retrofitex.util.OkHttpUtil
import edu.yujie.retrofitex.util.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val baseUrl = "https://my-json-server.typicode.com/"
    private val baseUrl2 = "https://jsonplaceholder.typicode.com/"
    private var manager = RetrofitManager.get(baseUrl, OkHttpUtil.get(this).client)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRetrofitBinding>(this, R.layout.activity_retrofit)

        binding.btnView.setOnClickListener {
            manager.changeBaseUrl(baseUrl)
            val apiService = manager.create<IApiService>()
            lifecycleScope.launch(Dispatchers.IO) {
                val githubBean = apiService.getGithubDatas()
                withContext(Dispatchers.Main) {
                    binding.tvView.text = githubBean.toString()
                }
            }
        }

        binding.btnView2.setOnClickListener {
            manager.changeBaseUrl(baseUrl2)
            val apiService2 = manager.create<IApiService2>()
            lifecycleScope.launch(Dispatchers.IO) {
                val response = apiService2.getOtherGithubData()
                withContext(Dispatchers.Main) {
                    binding.tvView.text = response.toString()
                }
            }
        }

    }
}