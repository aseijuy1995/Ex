package edu.yujie.coroutinesex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import edu.yujie.coroutinesex.databinding.ActivityMainBinding
import kotlinx.coroutines.*

//https://developer.android.com/kotlin/coroutines
//https://medium.com/jastzeonic/kotlin-coroutine-%E9%82%A3%E4%B8%80%E5%85%A9%E4%BB%B6%E4%BA%8B%E6%83%85-685e02761ae0
//https://github.com/Kotlin/kotlinx.coroutines/blob/master/docs/coroutines-guide.md

class CoroutinesActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val viewModel by viewModels<MyViewModel>()
    private val scope = CoroutineScope(Job() + Dispatchers.Main + CoroutineName("scope") + CoroutineExceptionHandler { context, t ->
//        t.printStackTrace()
        Snackbar.make(findViewById(android.R.id.content), "Exception: ${t.message}", Snackbar.LENGTH_SHORT).show()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

//        //lifecycleScope
//        lifecycleScope.launch(Dispatchers.Main) {
//            showData(binding)
//        }

        //--------------------------------------------------------------------------------

//        //viewModelScope
//        viewModel.loadData().observe(this) {
//            binding.tvView.text = it
//        }

        //--------------------------------------------------------------------------------

//        binding.tvView.setOnClickListener {
//            scope.launch {
//                for (i in 1..100) {
//                    delay(1000)
//                    println("$TAG scope $i")
//                    Snackbar.make(it, "scope $i", Snackbar.LENGTH_SHORT).show()
//                }
//            }
//        }

//        scope.launch {
//            delay(3000)
//            "2A".toInt()
//        }

        //--------------------------------------------------------------------------------

//        //series - 串聯
//        //series-outSide2
//        //delay = 6000o
//        //Hello Coroutines, name = series1
//        //delay = 3000
//        //Hello Coroutines, name = series2
//        //delay = 3000
//        //series
//        //series-outSide
//        lifecycleScope.launch(Dispatchers.IO) {
//            series()
//            println("series-outSide")
//        }
//        println("series-outSide2")

        //--------------------------------------------------------------------------------

//        //parallel - 併發
//        //parallel-outSide2
//        //delay = 1000
//        //parallel
//        //delay = 2000
//        //Hello Coroutines, name = parallel2
//        //delay = 3000
//        //Hello Coroutines, name = parallel1
//        //Hello Coroutines, name = parallel1: deferred
//        //Hello Coroutines, name = parallel2: deferred
//        //parallel-outSide
//        lifecycleScope.launch(Dispatchers.IO) {
//            parallel()
//            println("parallel-outSide")
//        }
//        println("parallel-outSide2")

        //--------------------------------------------------------------------------------

//        //eunBlocking:series - 串聯
//        //delay = 6000
//        //Hello Coroutines, name = series1
//        //delay = 3000
//        //Hello Coroutines, name = series2
//        //delay = 3000
//        //series
//        //delay = 2000
//        //series-outSide
//        //series-outSide2
//        lifecycleScope.launch {
//            runBlocking {
//                series()
//                delay(2000)
//                println("series-outSide")
//            }
//            println("series-outSide2")
//        }

        //--------------------------------------------------------------------------------

//        //runBlocking:parallel - 併發
//        //delay = 1000
//        //parallel
//        //delay = 2000
//        //Hello Coroutines, name = parallel2
//        //delay = 3000
//        //Hello Coroutines, name = parallel1
//        //Hello Coroutines, name = parallel: deferred
//        //Hello Coroutines, name = parallel2: deferred
//        //delay = 2000
//        //parallel-outSide
//        //parallel-outSide2
//        lifecycleScope.launch {
//            runBlocking {
//                parallel()
//                delay(2000)
//                println("parallel-outSide")
//            }
//            println("parallel-outSide2")
//        }

        //--------------------------------------------------------------------------------

//        //Integration
//        //delay = 1000
//        //CoroutinesActivity Integration runBlocking1
//        //CoroutinesActivity Integration outSide1
//        //delay = 3000
//        //Hello Coroutines, name = Integration3
//        //delay = 1000
//        //Hello Coroutines, name = Integration1
//        //delay = 3000
//        //Hello Coroutines, name = Integration4
//        //delay = 4000
//        //Hello Coroutines, name = Integration2
//        runBlocking {
//            lifecycleScope.launch {
//                val str = loadData(4000, "Integration1")
//                println(str)
//                val str2 = loadData(8000, "Integration2")
//                println(str2)
//            }
//            lifecycleScope.launch {
//                val str3 = loadData(3000, "Integration3")
//                println(str3)
//                val str4 = loadData(6000, "Integration4")
//                println(str4)
//            }
//            delay(1000)
//            println("$TAG Integration runBlocking1")
//        }
//        println("$TAG Integration outSide1")


    }

    private suspend fun showData(binding: ActivityMainBinding) {
        binding.tvView.text = loadData()
    }

    private suspend fun series() {
        val str = loadData(delay = 6000, name = "series1")
        println(str)
        val str2 = loadData(delay = 3000, name = "series2")
        println(str2)
        delay(3000)
        println("series")
    }

    private suspend fun parallel() {
        coroutineScope {
            val deferred = async {
                val str = loadData(delay = 6000, name = "parallel1")
                println(str)
                str
            }
            val deferred2 = async {
                val str = loadData(delay = 3000, name = "parallel2")
                println(str)
                str
            }
            delay(1000)
            println("parallel")
            val str = deferred.await()
            val str2 = deferred2.await()
            println("$str: deferred")
            println("$str2: deferred")
        }
    }

    override fun onPause() {
        super.onPause()
        scope.cancel()
    }

}