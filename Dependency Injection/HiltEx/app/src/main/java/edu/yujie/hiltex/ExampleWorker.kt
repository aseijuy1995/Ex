//package edu.yujie.hiltex
//
//import android.content.Context
//import androidx.hilt.Assisted
//import androidx.hilt.work.WorkerInject
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//
//class ExampleWorker @WorkerInject constructor(
//    @Assisted context: Context,
//    @Assisted params: WorkerParameters,
//    workerDependency: WorkerDependency
//) :
//    Worker(context, params) {
//    private val TAG = javaClass.simpleName
//
//    override fun doWork(): Result {
//        println("$TAG:doWork()")
//        return Result.success()
//    }
//
//}
//
//class WorkerDependency