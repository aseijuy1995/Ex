package edu.yujie.workmanagerex

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class ProgressWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    companion object {
        val PROGRESS = "PROGRESS"
    }

    override suspend fun doWork(): Result {
        for (i in 0..100) {
            val first = workDataOf(PROGRESS to i)
            setProgress(first)
            delay(10)
        }
        return Result.success()
    }
}