package edu.yujie.livedataex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
//class StockLiveData(val symbol: String) : LiveData<String>() {
class StockLiveData(val symbol: String) : MutableLiveData<String>() {
    private val TAG = javaClass.simpleName

    companion object {
        @Volatile
        private var sInstance: StockLiveData? = null

        fun get(symbol: String): StockLiveData =
            sInstance ?: synchronized(this) {
                sInstance ?: StockLiveData((symbol)).also {
                    sInstance = it
                }
            }
    }

    override fun onActive() {
        super.onActive()
        println("$TAG:onActive():symbol = $symbol")
    }

    override fun onInactive() {
        super.onInactive()
        println("$TAG:onInactive():symbol = $symbol")
    }


}