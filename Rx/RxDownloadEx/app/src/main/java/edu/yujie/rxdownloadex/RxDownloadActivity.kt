package edu.yujie.rxdownloadex

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.view.clicks
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.disposables.Disposable
import zlc.season.rxdownload4.manager.*

//https://github.com/ssseasonnn/RxDownload
//https://github.com/ssseasonnn/RxDownload/issues/73

/**
 * taskManager 下載 zip會 throws IllegalArgumentException: newLength < 0
 * 解決辦法: https://github.com/ssseasonnn/RxDownload/issues/73
 * */

class RxDownloadActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    //        private val url = "https://github.com/ssseasonnn/RxDownload/archive/master.zip"
    private val url =
        "https://apkpure.com/tw/line-free-calls-messages/jp.naver.line.android/download?from=versions"
    private var disposable: Disposable? = null
    private var taskManager: TaskManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnView = findViewById<Button>(R.id.btn_view)

        taskManager = url.manager()

        btnView.clicks()
            .compose(
                RxPermissions(this).ensure(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            .subscribe {
                if (it) {
//                    val exist = url.file().exists()
//                    println("$TAG exist = $exist")
//                    if (exist) {
//                        url.delete()
//                    }
//                    disposable = url.download()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeBy(
//                            onNext = {
//                                println("$TAG onNext() downloadSizeStr:${it.downloadSizeStr()} totalSizeStr:${it.totalSizeStr()}")
//                                val progressStr = "${it.downloadSizeStr()}/${it.totalSizeStr()}"
//                                btnView.text = progressStr
//
//                            },
//                            onComplete = {
//                                println("$TAG onComplete()")
//                                btnView.text = "onComplete()"
//                            },
//                            onError = {
//                                println("$TAG onError() it = $it")
//                                btnView.text = "onError()"
//                            }
//                        )

                    val exist = taskManager?.file()?.exists()
                    println("$TAG taskManager?.file() = ${taskManager?.file()}")
                    println("$TAG exist = $exist")
                    exist?.let {
                        if (it) {
                            taskManager?.delete()
                        }
                    }
                    taskManager?.start()
                } else {
                    Snackbar.make(btnView, "Deny", Snackbar.LENGTH_SHORT).show()
                }
            }

//        // 停止下載
//        disposable?.dispose()
//        taskManager?.stop()

//        // 取得文件
//        val file = url.file()
//        val file = taskManager?.file()

//        //刪除下載文件
//        url.delete()
//        taskManager?.delete()

//        //開始下載
//        taskManager?.start()

//        //取消訂閱
//        taskManager?.dispose(tag)

//        //刪除下載
//        taskManager?.delete()

        taskManager?.subscribe {
            when (it) {
                is Normal -> {
                    println("$TAG Normal")
                }
                is Started -> {
                    println("$TAG Started")
                }
                is Downloading -> {
                    println("$TAG Downloading")
                }
                is Paused -> {
                    println("$TAG Paused")
                }
                is Completed -> {
                    println("$TAG Completed")
                }
                is Failed -> {
                    println("$TAG Failed")
                }
                is Deleted -> {
                    println("$TAG Deleted")
                }
                else -> {
                    println("$TAG $it")
                }
            }
        }

//        // 查詢task
//        RxDownloadRecorder.getTaskList(url)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy {  }

//        // 查詢all task
//        RxDownloadRecorder.getAllTask()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy {  }

//        // 查詢all下載狀態
//        RxDownloadRecorder.getAllTaskWithStatus()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeBy {  }

//        // 全部開始
//        RxDownloadRecorder.startAll()

//        // 全部停止
//        RxDownloadRecorder.stopAll()

//        // 刪除所有
//        RxDownloadRecorder.deleteAll()
    }

}