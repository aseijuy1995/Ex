package edu.yujie.livedatabus

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

//https://www.jianshu.com/p/9b00422fbcc1
//https://tech.meituan.com/2018/07/26/android-livedatabus.html

//https://www.shangmayuan.com/a/dd386a5a7c294c31b65c1847.html

class LiveDataBusActivity : AppCompatActivity() {
    private val tvViewNameTag = "tv_view_name_tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)

        val str = "Hello LiveDataBus"
        LiveDataBus.send(tvViewNameTag, str)

        tvView.setOnClickListener {
//            startActivity(Intent(this, LiveDataBus2Activity::class.java))
            LiveDataBus.with<String>(tvViewNameTag)?.observe(this) {
                tvView.text = it
            }
        }

//        LiveDataBus.with<String>(tvViewNameTag)?.observe(this) {
//            tvView.text = it
//        }
    }
}