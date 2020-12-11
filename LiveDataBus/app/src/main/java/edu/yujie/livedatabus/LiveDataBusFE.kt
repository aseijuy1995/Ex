package edu.yujie.livedatabus

import androidx.lifecycle.MutableLiveData


/**
 * 初版 -
 * LiveData在分頁未訂閱的情況下,主頁發送數據顯示
 * 切至分頁時,因呼叫observe()再次訂閱,導致主頁發送數據再次拋出顯示於分頁
 * */

object LiveDataBusFE {
    private val liveDataMap: HashMap<String, MutableLiveData<Any?>> by lazy { HashMap<String, MutableLiveData<Any?>>() }

    fun <T> with(key: String): MutableLiveData<T> {
        if (!liveDataMap.containsKey(key)) {
            liveDataMap[key] = MutableLiveData()
        }
        return liveDataMap[key] as MutableLiveData<T>
    }
}