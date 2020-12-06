package edu.yujie.rxbusex

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxBus2Activity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)

        RxBus.observable<DataEvent>()
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val data = it.data
                println("$TAG id = ${data.id}, name = ${data.name}")
                tvView.text = "id = ${data.id}, name = ${data.name}"
            }.registerInBus(this)

        //--------------------------------------------------------------------------------

        tvView.setOnClickListener {
            RxBus.send(
                DataListEvent(
                    Data(id = 2, name = "Chen"),
                    Data(id = 3, name = "Yu"),
                    Data(id = 4, name = "Jie")
                )
            )
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unRegister(this)
    }
}