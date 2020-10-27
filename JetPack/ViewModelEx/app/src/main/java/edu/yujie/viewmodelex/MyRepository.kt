package edu.yujie.viewmodelex

import androidx.lifecycle.MutableLiveData

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MyRepository {

    val users: MutableLiveData<List<String>> by lazy {
        MutableLiveData(listOf("1", "2", "3"))
    }
}