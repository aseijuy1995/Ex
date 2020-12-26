package edu.yujie.okhttpex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.okhttpex.databinding.ActivityOkhttpBinding

class OkHttpActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_okhttp)
        val binding = DataBindingUtil.setContentView<ActivityOkhttpBinding>(this, R.layout.activity_okhttp)

//        //get:sync
//        val url = "http://localhost:8080/get"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncGet(url, mapOf("param" to "getParam"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //get:async
//        val url = "http://localhost:8080/get"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncGet(url, mapOf("param" to "getParam"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

        //--------------------------------------------------------------------------------

//        //head:sync
//        val url = "http://localhost:8080/head"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//            val head = OkHttpUtil.get(this@OkHttpActivity).syncHead(url, mapOf("param" to "headParam"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = head
//            }
//        }

//        //head:async
//        val url = "http://localhost:8080/head"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//        OkHttpUtil.get(this@OkHttpActivity).asyncHead(url, mapOf("param" to "getParam"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.headers.toString()
//                    }
//
//            }
//        })

        //--------------------------------------------------------------------------------

//        //post:json:sync
//        val url = "http://localhost:8080/post/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPostJson(url, "{\"param\":\"postJson\"}")
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //post:from-data:sync
//        val url = "http://localhost:8080/post/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPostFromData(url, mapOf("param" to "postFromData"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //post:json:async
//        val url = "http://localhost:8080/post/json"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPostJson(url, "{\"param\":\"postJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

//        //post:from-data:async
//        val url = "http://localhost:8080/post/from-data"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPostFromData(url, mapOf("param" to "postFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

        //--------------------------------------------------------------------------------

//        //delete:json:sync
//        val url = "http://localhost:8080/delete/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncDeleteJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //delete:from-data:sync
//        val url = "http://localhost:8080/delete/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncDeleteFromData(url, mapOf("param" to "deleteFromData"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //delete:json:async
//        val url = "http://localhost:8080/delete/json"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncDeleteJson(url, "{\"param\":\"deleteJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

//        //delete:from-data:async
//        val url = "http://localhost:8080/delete/from-data"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncDeleteFromData(url, mapOf("param" to "deleteFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

        //--------------------------------------------------------------------------------

//        //put:json:sync
//        val url = "http://localhost:8080/put/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPutJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //put:from-data:sync
//        val url = "http://localhost:8080/put/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPutFromData(url, mapOf("param" to "putFromData"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //put:json:async
//        val url = "http://localhost:8080/put/json"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPutJson(url, "{\"param\":\"putJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

//        //put:from-data:async
//        val url = "http://localhost:8080/put/from-data"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPutFromData(url, mapOf("param" to "putFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

        //--------------------------------------------------------------------------------

//        //patch:json:from-data:sync
//        val url = "http://localhost:8080/patch/json"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPatchJson(url, "{\"param\":\"deleteJson\"}")
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //patch:from-data:sync
//        val url = "http://localhost:8080/patch/from-data"
//        lifecycleScope.launch(Dispatchers.IO) {
//            OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            val body = OkHttpUtil.get(this@OkHttpActivity).syncPatchFromData(url, mapOf("param" to "patchFromData"))
//            withContext(Dispatchers.Main) {
//                binding.tvView.text = body
//            }
//        }

//        //patch:json:async
//        val url = "http://localhost:8080/patch/json"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPatchJson(url, "{\"param\":\"patchJson\"}", object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

//        //patch:from-data:async
//        val url = "http://localhost:8080/patch/from-data"
//        OkHttpUtil.get(this@OkHttpActivity).loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        OkHttpUtil.get(this@OkHttpActivity).asyncPatchFromData(url, mapOf("param" to "patchFromData"), object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful)
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        binding.tvView.text = response.body?.string()
//                    }
//            }
//        })

    }
}