package edu.yujie.workmanagerex

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class CoroutineDownloadWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val TAG = javaClass.simpleName

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val job = (0 until 10).map {
            async {
                downloadSynchronously("Hello CoroutineWorker:$it")
            }
        }

        job.awaitAll()
        Result.success()
    }

    private fun downloadSynchronously(str: String) {
        println("$TAG: $str")
    }
}