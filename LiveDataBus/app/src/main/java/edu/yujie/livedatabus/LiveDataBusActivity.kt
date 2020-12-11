package edu.yujie.livedatabus

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar

//https://tech.meituan.com/2018/07/26/android-livedatabus.html
//https://chsmy.github.io/2019/04/27/technology/%E4%BA%8B%E4%BB%B6%E6%80%BB%E7%BA%BFLiveDataBus/
//https://www.shangmayuan.com/a/dd386a5a7c294c31b65c1847.html

class LiveDataBusActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    companion object {
        val tag = "tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(android.R.id.content)
        val btnView = findViewById<Button>(R.id.btn_view)
        val btnViewGo = findViewById<Button>(R.id.btn_view2)

        btnViewGo.setOnClickListener {
            startActivity(Intent(this, LiveDataBus2Activity::class.java))
        }

        btnView.setOnClickListener {
//            LiveDataBusFE.with<String>(tag).postValue("Hello LiveDataBusActivity")
            LiveDataBus.with<String>(tag).postValue("Hello LiveDataBusActivity")
        }

//        LiveDataBusFE.with<String>(tag).observe(this, Observer {
//            println("$TAG onNext() it = $it")
//            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
//        })

        LiveDataBus.with<String>(tag).observe(this, Observer {
            println("$TAG onNext() it = $it")
            Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
        })

    }
}