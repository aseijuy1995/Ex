package edu.yujie.lifecyclesex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import edu.yujie.lifecyclesex.common.MyDefaultObserver
import edu.yujie.lifecyclesex.reactivestreams.MyReactiveStreams
import edu.yujie.lifecyclesex.service.MyService

class MainAppCompatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appcompatactivity_main)
        MyObserver(this)
        MyDefaultObserver(this)
        //Process
        findViewById<View>(R.id.cLayout).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

        //Service
        findViewById<View>(R.id.btnStart).setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }

        findViewById<View>(R.id.btnStop).setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }

        //ReactiveStreams
        MyReactiveStreams().dataLiveData.observe(this, Observer {
            Toast.makeText(this, "dataLiveData = $it", Toast.LENGTH_SHORT).show()
        })
    }

}