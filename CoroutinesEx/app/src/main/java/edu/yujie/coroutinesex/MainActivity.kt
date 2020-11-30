package edu.yujie.coroutinesex

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import edu.yujie.coroutinesex.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

//        //lifecycleScope
//        lifecycleScope.launch(Dispatchers.IO) {
//            showData(binding)
//        }

//        //viewModelScope
//        viewModel.loadData().observe(this) {
//            binding.tvView.text = it
//        }

//        //series - 串聯
//        lifecycleScope.launch(Dispatchers.IO) {
//            series()
//        }

//        //parallel - 並發
//        lifecycleScope.launch(Dispatchers.IO) {
//            parallel()
//        }

    }

    private suspend fun showData(binding: ActivityMainBinding) = withContext(Dispatchers.Main) {
        binding.tvView.text = loadData()
    }

    // delay = 6000
    // series1
    // delay = 3000
    // series2
    // delay = 3000
    // series
    private suspend fun series() {
        val str = loadData(delay = 6000, name = "series1")
        println(str)
        val str2 = loadData(delay = 3000, name = "series2")
        println(str2)
        delay(3000)
        println("series")
    }

    // delay = 1000
    // parallel
    // delay = 2000
    // Hello Coroutines, name = parallel2
    // delay = 3000
    // Hello Coroutines, name = parallel1
    // Hello Coroutines, name = parallel1: deferred
    // Hello Coroutines, name = parallel2: deferred
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

}