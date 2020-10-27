package edu.yujie.appstartupex

import androidx.work.WorkManager

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */
class ExampleLogger private constructor(workManager: WorkManager) {
    private val TAG = javaClass.simpleName

    init {
        println("$TAG:init()")
    }

    companion object {
        @Volatile
        private var sInstance: ExampleLogger? = null

        fun getInstance(workManager: WorkManager) =
            sInstance ?: synchronized(this) {
                sInstance ?: ExampleLogger(workManager).apply {
                    sInstance = this
                }
            }
    }
}