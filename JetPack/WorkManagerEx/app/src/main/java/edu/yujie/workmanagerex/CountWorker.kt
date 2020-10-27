package edu.yujie.workmanagerex

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Thread.sleep

/**
 * @author YuJie on 2020/10/28
 * @describe 說明
 * @param 參數
 */
class CountWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    private val TAG = javaClass.simpleName

    override fun doWork(): Result {
        loadCount()
        return Result.success()
    }

    private fun loadCount() {
        for (i in 1..100) {
            println("$TAG:loadCount() i = $i")
            sleep(1000L)
        }
    }

}