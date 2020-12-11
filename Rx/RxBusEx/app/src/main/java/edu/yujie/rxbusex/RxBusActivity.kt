package edu.yujie.rxbusex

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

//https://www.jianshu.com/p/ca090f6e2fe2
//https://www.jianshu.com/p/f6fd651abc8a
//https://juejin.cn/post/6844903482000932878

class RxBusActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)

        tvView.setOnClickListener {
            val data = Data(1, "Yujie")
            RxBus.send(DataEvent(data))
            startActivity(Intent(this, RxBus2Activity::class.java))
        }

        //--------------------------------------------------------------------------------

        RxBus.observable<DataListEvent>()
            .doOnNext {
                tvView.text = "doOnNext..."
            }
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val datas = it.datas
                println("$TAG datas = $datas")
                val list = it.datas.map {
                    "id = ${it.id}, name:${it.name}\t"
                }
                tvView.text = list.toString()
            }.registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }

}