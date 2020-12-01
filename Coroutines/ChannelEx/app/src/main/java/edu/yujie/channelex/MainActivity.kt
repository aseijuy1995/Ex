package edu.yujie.channelex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//https://medium.com/jastzeonic/kotlin-coroutine-channels-%E9%82%A3%E4%B8%80%E5%85%A9%E4%BB%B6%E4%BA%8B%E6%83%85-3a3e2aef75d9
class MainActivity : AppCompatActivity() {
    private val channel1 = Channel<String>()
    private val channel3 = Channel<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        // delay = 1000
//        // delay = 1000
//        // delay = 1000
//        // result1 result2 result3 result4
//        lifecycleScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
//                val result1 = channel1.receive()
//                val result3 = channel3.receive()
//
//                val result2 = async {
//                    loadData(1000, "$result1 result2")
//                }
//                val result4 = async {
//                    loadData(1000, "$result3 result4")
//                }
//                println("${result2.await()} ${result4.await()}")
//            }
//            println("time:$time")
//        }
//
//        lifecycleScope.launchWhenStarted {
//            delay(1000)
//            channel1.send("result1")
//            delay(1000)
//            channel3.send("result3")
//        }

//        val channel = Channel<Int>(4)
//        lifecycleScope.launch {
//            while (!channel.isClosedForSend) {
//                delay(1000)
//                println("left send print :${channel.receive()}")
//            }
//        }
//        lifecycleScope.launch {
//            while (!channel.isClosedForSend) {
//                delay(1000)
//                println("right send print :${channel.receive()}")
//            }
//        }
//
//        lifecycleScope.launch {
//            for (i in 0..4) {
//                channel.send(i)
//            }
//        }

//        val channel = Channel<Int>(2)
//        val offerResult = channel.offer(1)
//        println("is offer result success: $offerResult")
//
//        val pollResult = channel.poll()
//        println("is poll result success: $pollResult")

//        val channel = lifecycleScope.produce<Int> {
//            println("start produce")
//            println("do something")
//            send(1)
//            send(2)
//        }
//
//        lifecycleScope.launch {
//            delay(1000)
//            println("getting start to receive")
//            val result = channel.receive()
//            println("get result:$result")
//            val result2 = channel.receive()
//            println("get result2:$result2")
//        }



    }
}