package edu.yujie.workmanagerex

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.lang.Thread.sleep

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val TAG = javaClass.simpleName

    override fun doWork(): Result {
        val data = inputData.getString("data")

        setProgressAsync(workDataOf("value" to "0"))
        sleep(500)
        for (i in 0..100) {
            uploadImage(data)
        }
        return Result.success(workDataOf("data" to "from worker"))
//        return Result.retry()
    }

    private fun uploadImage(data: String?) {
        println("$TAG: uploadImage(), data:$data")
    }

    override fun onStopped() {
        super.onStopped()
        println("$TAG: onStopped()")
    }
}