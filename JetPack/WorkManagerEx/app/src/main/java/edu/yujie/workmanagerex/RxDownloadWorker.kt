package edu.yujie.workmanagerex

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Observable
import io.reactivex.Single

class RxDownloadWorker(context: Context, params: WorkerParameters) : RxWorker(context, params) {
    private val TAG = javaClass.simpleName

    override fun createWork(): Single<Result> =
        Observable.range(0, 10)
            .flatMap {
                download("Hello, RxWorker:$it")
            }
            .toList()
            .map {
                Result.success()
            }

    private fun download(str: String): Observable<String> {
        println("$TAG: $str")
        return Observable.just(str)
    }
}