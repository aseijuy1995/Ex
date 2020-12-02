package edu.yujie.flowex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch

//https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/
//https://medium.com/jastzeonic/kotlin-coroutines-flow-%E9%82%A3%E4%B8%80%E5%85%A9%E4%BB%B6%E4%BA%8B%E6%83%85-7146911bc18f
//https://juejin.cn/post/6844904057530908679
//https://proandroiddev.com/kotlin-flow-benefits-over-rxjava-b220658f1a92
class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvView = findViewById<TextView>(R.id.tv_view)

        lifecycleScope.launch {
//            // map
//            flowDemo().map { (it * it).toString() }
//                .collect {
//                    println("map: $it")
//                }

//            // transform
//            flowDemo().transform<Int, String> { emit((it * it).toString()) }
//                .collect{
//                    println("transform: $it")
//                }

//            // take
//            flowDemo().take(2)
//                .collect {
//                    println("take: $it")
//                }

//            // filter
//            flowDemo().filter { it == 2 }
//                .collect{
//                    println("filter: $it")
//                }

//            // filterNot
//            flowDemo().filterNot { it==2 }
//                .collect{
//                    println("filterNot: $it")
//                }

//            // flowOn
//            flowDemo().flowOn(Dispatchers.IO)
//                .collect{
//                    println("flowOn: $it")
//                }

//            // buffer
//            flowDemo().buffer()
//                .collect{
//                    delay(1000)
//                    println("buffer: $it")
//                }

//            // conflate
//            flowDemo().conflate()
//                .collect{
//                    delay(2000)
//                    println("conflate: $it")
//                }

//            // collectLatest
//            flowDemo().collectLatest {
//                delay(1100)
//                println("collectLatest: $it")
//            }

//            // zip
//            flowDemo().zip(flowDemo2()) { t1, t2 ->
//                delay(1000)
//                "t1:$t1 + t2:$t2"
//            }
//                .collect {
//                    println("zip: $it")
//                }

//            // combine
//            flowDemo().combine(flowDemo2()) { t1, t2 ->
////                delay(1000)
//                "t1:$t1 + t2:$t2"
//            }
//                .collect {
//                    println("combine: $it")
//                }

//            // flowMapConcat - 串聯
//            flowDemo().flatMapConcat {
//                println("flowDemo: flatMapConcat: $it")
//                flowDemo2()
//            }
//                .collect {
//                    println("flatMapConcat: $it")
//                }

//            // flatMapMerge - 併發
//            flowDemo().flatMapMerge {
//                println("flowDemo: flatMapMerge: $it")
//                flowDemo2()
//            }
//                .collect {
//                    println("flatMapMerge: $it")
//                }

//            // flatMapLatest
//            flowDemo().flatMapLatest {
//                println("flowDemo: flatMapLatest: $it")
//                delay(1100)
//                flowDemo2()
//            }
//                .collect{
//                    println("flatMapLatest: $it")
//                }

//            // catch
//            flowDemo().flatMapMerge {
//                flow { emit(if(it==3) null else it) }
//            }
//                .map {
//                    it!!*it
//                }
//                .catch { Toast.makeText(this@MainActivity, "Error:${it.message}", Toast.LENGTH_SHORT).show() }
//                .collect{
//                    println("catch: $it")
//                }

            // onCompletion
//            flowDemo().onCompletion {
//                println("onCompletion: Done")
//            }
//                .collect{
//                    println("onCompletion: $it")
//                }

//            // reduce
//            val sum = flowDemo().reduce { accumulator, value ->
//                println("accumulator:$accumulator value:$value")
//                accumulator + value
//            }
//            println("sum:$sum")


            val sum = flowDemo().fold(0) { acc, value ->
                println("acc:$acc value:$value")
                acc + value
            }
            println("sum:$sum")


        }
    }

}