package edu.yujie.livedatabus

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LiveDataBus2Activity : AppCompatActivity() {
    private val tvViewNameTag = "tv_view_name_tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvView = findViewById<TextView>(R.id.tv_view)

//        tvView.setOnClickListener {
//            val str = "Hello LiveDataBus"
//            LiveDataBus.send(tvViewNameTag, str)
//
//        }

        LiveDataBus.with<String>(tvViewNameTag)?.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            tvView.text = it
        }
    }
}