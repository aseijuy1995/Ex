package edu.yujie.coroutinesex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                delay(12000L)
            }
            Toast.makeText(this@MainActivity, "123", Toast.LENGTH_SHORT).show()
        }

        //runBlocking:workThread
        //runBlocking:1000L
        //runBlocking:2000L
        runBlocking {
            launch {
                delay(2000L)
                println("runBlocking:2000L")
            }
            launch {
                delay(1000L)
                println("runBlocking:1000L")
            }
            println("runBlocking:workThread")
        }
        println("runBlocking----------------")

        //runBlocking2:launch1-1
        //runBlocking2:1000L
        //runBlocking2:2000L
        //runBlocking2:2000L-deferred1
        //runBlocking2:1000L-deferred2
        //runBlocking2:launch1-2
        //runBlocking2:launch2
        //runBlocking2:launch
        //runBlocking2:mainThread
        runBlocking {
            val deferred1 = async {
                delay(2000L)
                val msg = "runBlocking2:2000L"
                println(msg)
                "$msg - deferred1"
            }
            val deferred2 = async {
                delay(1000L)
                val msg = "runBlocking2:1000L"
                println(msg)
                "$msg - deferred2"
            }
            println("runBlocking2:launch1-1")
            val str1 = deferred1.await()
            println(str1)
//            delay(2000L)
            val str2 = deferred2.await()
            println(str2)
            println("runBlocking2:launch1-2")
        }
        runBlocking {
            launch {
                println("runBlocking2:launch")
            }
            println("runBlocking2:launch2")
        }
        println("runBlocking2:mainThread")
    }
}