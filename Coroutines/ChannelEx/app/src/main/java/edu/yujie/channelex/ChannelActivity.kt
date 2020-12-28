package edu.yujie.channelex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.channels.Channel

//https://medium.com/jastzeonic/kotlin-coroutine-channels-%E9%82%A3%E4%B8%80%E5%85%A9%E4%BB%B6%E4%BA%8B%E6%83%85-3a3e2aef75d9

class ChannelActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private val channel1 = Channel<String>()
    private val channel2 = Channel<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

//        //time = 2xxx
//        lifecycleScope.launch {
//            val time = measureTimeMillis {
//                val result1 = channel1.receive()
//                val result2 = channel2.receive()
//                val result3 = async {
//                    "result3:($result1)"
//                }
//                val result4 = async {
//                    "result4:($result2)"
//                }
//                println("$TAG result1:$result1, result2:$result2, result3:$result3, result4:$result4")
//            }
//            println("$TAG time:$time")
//        }
//
//        lifecycleScope.launchWhenStarted {
//            delay(2000)
//            channel1.send("result1")
//        }
//        lifecycleScope.launchWhenCreated {
//            delay(1000)
//            channel2.send("result2")
//        }

        //--------------------------------------------------------------------------------

//        val channel3 = Channel<Int>()
//        lifecycleScope.launch {
//            for (i in 0..10) {
//                channel3.send(i)
//            }
//        }
//
//        lifecycleScope.launch {
//            while (true) {
//                delay(100)
//                val result = channel3.receive()
//                println("$TAG result1:$result")
//            }
//        }
//        lifecycleScope.launch {
//            while (true) {
//                delay(100)
//                val result = channel3.receive()
//                println("$TAG result2:$result")
//            }
//        }

        //--------------------------------------------------------------------------------

    }
}