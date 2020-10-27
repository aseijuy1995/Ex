package edu.yujie.workmanagerex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = OneTimeWorkRequestBuilder<CountWorker>().build()
        WorkManager.getInstance(this).enqueue(request)
        
    }
}