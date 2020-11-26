package edu.yujie.workmanagerex

import android.content.Context
import android.os.SystemClock.sleep
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.concurrent.thread

class DownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val TAG = javaClass.simpleName

    override fun doWork(): Result {
        for (i in 0..10) {
            try {
                println("isStopped:$isStopped")
                if (isStopped)
                    break
                thread {
                    sleep(500)
                    downloadSynchronously("downloadSynchronously:$i")
                }
            } catch (e: Exception) {
                return Result.failure()
            }
        }
        return Result.success()
    }

    private fun downloadSynchronously(str: String) {
        println("$TAG: downloadSynchronously():$str")
    }

    override fun onStopped() {
        super.onStopped()
        println("$TAG: onStopped()")
    }
}