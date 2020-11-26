package edu.yujie.workmanagerex

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*

//https://developer.android.com/topic/libraries/architecture/workmanager/how-to/testing-worker-impl
//https://developer.android.com/topic/libraries/architecture/workmanager/how-to/integration-testing

//https://medium.com/@carterchen247/background-processing-after-targeting-android-oreo-using-workmanager-2fbc86ff5388
//https://juejin.cn/post/6844903955290390541
//https://blog.csdn.net/jifashihan/article/details/81871322
class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = WorkManager.getInstance(this)

//        val request = OneTimeWorkRequest.from(UploadWorker::class.java)
        val request = OneTimeWorkRequestBuilder<UploadWorker>().build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

//        val request = PeriodicWorkRequestBuilder<UploadWorker>(15, TimeUnit.MINUTES)
////            .setConstraints(constraints)
////            .setInitialDelay(3, TimeUnit.SECONDS)
////            .setBackoffCriteria(
////                BackoffPolicy.EXPONENTIAL,
////                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
////                TimeUnit.MILLISECONDS
////            )
//            .addTag("cleanTAG")
//            .setInputData(workDataOf("data" to "Worker"))
//            .build()

        manager.getWorkInfoByIdLiveData(request.id).observe(this) {
            println("it.state:${it.state}")

            when (it.state) {
                WorkInfo.State.ENQUEUED -> {

                }

                WorkInfo.State.RUNNING -> {
                    val value = it.progress.getString("value")
                    println("$TAG: value = $value")
                }
                WorkInfo.State.SUCCEEDED -> {
                    it.outputData.getString("data")?.also {
                        println("$TAG data:$it")
                    }
                }
                else -> {
                    println("$TAG data")
                }
            }
        }

//        manager.enqueue(request)

        //??
//        manager.enqueueUniqueWork(
//            "",
//            "",
//            request
//        )

//        manager.enqueueUniquePeriodicWork(
//            "sendLogs",
//            ExistingPeriodicWorkPolicy.KEEP,
//            request
//        )
        //

        val requestWorker = OneTimeWorkRequestBuilder<DownloadWorker>().build()
        val requestCoroutine = OneTimeWorkRequestBuilder<CoroutineDownloadWorker>().build()
        val requestRx = OneTimeWorkRequestBuilder<RxDownloadWorker>().build()
        manager.beginWith(requestWorker)
            .then(requestCoroutine)
            .then(requestRx)
            .enqueue()


//        //worker
//        val request = OneTimeWorkRequestBuilder<DownloadWorker>().build()
//        manager.enqueue(request)

//        //coroutineWorker
//        val requestCoroutine = OneTimeWorkRequestBuilder<CoroutineDownloadWorker>().build()
//        manager.enqueue(requestCoroutine)

//        //rxWorker
//        val requestRx = OneTimeWorkRequestBuilder<RxDownloadWorker>().build()
//        manager.enqueue(requestRx)

        findViewById<TextView>(R.id.tv_view).setOnClickListener {
            Toast.makeText(this, "cancel worker by TAG", Toast.LENGTH_SHORT).show()
            manager.cancelAllWorkByTag("cleanTAG")
        }
    }
}