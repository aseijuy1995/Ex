package edu.yujie.rxlifecycleex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.trello.rxlifecycle4.android.lifecycle.kotlin.bindToLifecycle
import edu.yujie.rxlifecycleex.databinding.ActivityRxLifecycleBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

//https://github.com/trello/RxLifecycle
//https://www.itread01.com/content/1542927306.html

class RxLifecycleActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityRxLifecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityRxLifecycleBinding>(this, R.layout.activity_rx_lifecycle)

        binding.btnView.setOnClickListener {
            Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)//取消此行會導致記憶體洩漏,切換頁面會繼續print()出資料
                .subscribe {
                    println("$TAG onNext() it = $it")
                    val sf = String.format("it = %s", it.toString())
                    binding.tvText.text = sf
                }
            thread {
                sleep(3000)
                startActivity(Intent(this, RxLifecycleActivity2::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("$TAG onDestroy")
    }

}