package edu.yujie.lifecyclesex

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import edu.yujie.lifecyclesex.process.MyProcessObserver

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyProcessObserver(ProcessLifecycleOwner.get())
    }
}