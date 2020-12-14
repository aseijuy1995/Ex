package edu.yujie.okhttpex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)

        //Get
//        //sync - get
//        lifecycleScope.launch(Dispatchers.IO) {
//            val str = OkHttpUtil.get(this@MainActivity).syncGet(url, mapOf("method" to "doFindTypeJ", "category" to "6"))
//            withContext(Dispatchers.Main) {
//                tvView.text = str
//            }
//        }

//        //async - get
//        OkHttpUtil.get(this@MainActivity).asyncGet(url, mapOf("method" to "doFindTypeJ", "category" to "6"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                println("$TAG onFailure()")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                println("$TAG onResponse()")
//                val str = response.body?.string()
//
//                lifecycleScope.launch(Dispatchers.Main) {
//                    tvView.text = str
//                }
//            }
//        })

        //--------------------------------------------------------------------------------

        //Post
//        //sync - post
//        lifecycleScope.launch(Dispatchers.IO) {
//            val str = OkHttpUtil.get(this@MainActivity).syncPost(url, mapOf("cmd" to "get_version_android"))
//            withContext(Dispatchers.Main) {
//                tvView.text = str
//            }
//        }

//        //async - post
//        OkHttpUtil.get(this).asyncPost(url, mapOf("cmd" to "get_version_android"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                println("$TAG onFailure()")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                println("$TAG onResponse()")
//                val str = response.body?.string()
//
//                lifecycleScope.launch(Dispatchers.Main) {
//                    tvView.text = str
//                }
//            }
//        })

    }
}