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

//        //get:sync
//        val url = "http://localhost:8080/get"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncGet(url, mapOf("param" to "getParam"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //get:async
//        val url = "http://localhost:8080/get"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncGet(url, mapOf("param" to "getParam"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

        //--------------------------------------------------------------------------------

//        //head:sync
//        val url = "http://localhost:8080/head"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//            val body = OkHttpUtil.get(this@MainActivity).syncHead(url, mapOf("param" to "headParam"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //head:async
//        val url = "http://localhost:8080/head"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//        OkHttpUtil.get(this@MainActivity).asyncHead(url, mapOf("param" to "getParam"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.headers.toString()
//            }
//        })


        //--------------------------------------------------------------------------------

//        //post:json:sync
//        val url = "http://localhost:8080/post/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPostJson(url, "{\"param\":\"postJson\"}")
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //post:from-data:sync
//        val url = "http://localhost:8080/post/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPostFromData(url, mapOf("param" to "postFromData"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //post:json:async
//        val url = "http://localhost:8080/post/json"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPostJson(url, "{\"param\":\"postJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

//        //post:from-data:async
//        val url = "http://localhost:8080/post/from-data"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPostFromData(url, mapOf("param" to "postFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

        //--------------------------------------------------------------------------------

//        //delete:json:sync
//        val url = "http://localhost:8080/delete/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncDeleteJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //delete:from-data:sync
//        val url = "http://localhost:8080/delete/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncDeleteFromData(url, mapOf("param" to "deleteFromData"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //delete:json:async
//        val url = "http://localhost:8080/delete/json"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncDeleteJson(url, "{\"param\":\"deleteJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

//        //delete:from-data:async
//        val url = "http://localhost:8080/delete/from-data"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncDeleteFromData(url, mapOf("param" to "deleteFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

        //--------------------------------------------------------------------------------

//        //put:json:sync
//        val url = "http://localhost:8080/put/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPutJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //put:from-data:sync
//        val url = "http://localhost:8080/put/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPutFromData(url, mapOf("param" to "putFromData"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //put:json:async
//        val url = "http://localhost:8080/put/json"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPutJson(url, "{\"param\":\"putJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

//        //put:from-data:async
//        val url = "http://localhost:8080/put/from-data"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPutFromData(url, mapOf("param" to "putFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

        //--------------------------------------------------------------------------------

//        //patch:json:from-data:sync
//        val url = "http://localhost:8080/patch/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPatchJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //patch:from-data:sync
//        val url = "http://localhost:8080/patch/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@MainActivity).syncPatchFromData(url, mapOf("param" to "patchFromData"))
//            withContext(Dispatchers.Main) {
//                tvView.text = body
//            }
//        }

//        //patch:json:async
//        val url = "http://localhost:8080/patch/json"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPatchJson(url, "{\"param\":\"patchJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

//        //patch:from-data:async
//        val url = "http://localhost:8080/patch/from-data"
//        OkHttpUtil.get(this@MainActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@MainActivity).asyncPatchFromData(url, mapOf("param" to "patchFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    tvView.text = response.body?.string()
//            }
//        })

    }
}