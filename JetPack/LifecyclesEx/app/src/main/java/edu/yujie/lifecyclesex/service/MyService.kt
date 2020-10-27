package edu.yujie.lifecyclesex.service

import androidx.lifecycle.LifecycleService

/**
 * @author YuJie on 2020/10/27
 * @describe 說明
 * @param 參數
 */
class MyService : LifecycleService() {

    init {
        MyServiceObserver(this)
    }

}