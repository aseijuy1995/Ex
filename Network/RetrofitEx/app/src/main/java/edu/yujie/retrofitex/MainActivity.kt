package edu.yujie.retrofitex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitManager.init(this)

        lifecycleScope.launch(Dispatchers.IO) {
            val appResult = RetrofitManager.create<IApiService>().post()
            println("$TAG appresult:$appResult")
        }
    }
}