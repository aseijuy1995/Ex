package edu.yujie.roomex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.get(this, lifecycleScope)
        val myDao = db.myDao

//        lifecycleScope.launch(Dispatchers.IO) {
//            myDao.queryFirst().collect {
//                it.map {
//                    println("$TAG: First:$it")
//                }
//            }
//        }

//        lifecycleScope.launch(Dispatchers.IO) {
//            myDao.querySecond().collect {
//                it.map {
//                    println("$TAG: Second:$it")
//                }
//            }
//        }

//        lifecycleScope.launch(Dispatchers.IO) {
//            myDao.queryFirstSecond(10).collect {
//                it.map {
//                    println("$TAG: FirstSecond:$it")
//                }
//            }
//        }

        lifecycleScope.launch(Dispatchers.IO) {
            myDao.queryThird().collect {
                println("$TAG: $it")
                it.map {
                    println("$TAG: Third:$it")
                }
            }
        }
    }
}