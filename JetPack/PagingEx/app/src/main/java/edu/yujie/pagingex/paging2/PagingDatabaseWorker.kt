package edu.yujie.pagingex.paging2

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.yujie.pagingex.Concert
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent

class PagingDatabaseWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val TAG = javaClass

    override suspend fun doWork(): Result =
        try {
            val list = mutableListOf<Concert>()
            for (i in 1..197 step 2) {
                val concert = Concert(id = i, name = "Name = $i")
                list.add(concert)
            }
//            AppDatabase.get(applicationContext).concertDao.insertAll(*list.toTypedArray())
            println("$TAG:doWork = success")
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            println("$TAG:doWork = failure")
            Result.failure()
        }
}