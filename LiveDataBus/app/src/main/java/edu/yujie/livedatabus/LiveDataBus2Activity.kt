package edu.yujie.livedatabus

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import edu.yujie.livedatabus.LiveDataBusActivity.Companion.tag

class LiveDataBus2Activity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(android.R.id.content)
        val btnView = findViewById<TextView>(R.id.btn_view)
        val btnViewBack = findViewById<TextView>(R.id.btn_view2)

        btnViewBack.setOnClickListener {
            finish()
        }

        btnView.setOnClickListener {
//            LiveDataBusFE.with<String>(tag).postValue("Hello LiveDataBus2Activity")
            LiveDataBus.with<String>(tag).postValue("Hello LiveDataBus2Activity")
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