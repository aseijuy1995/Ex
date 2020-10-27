package edu.yujie.livedataex

import androidx.lifecycle.MutableLiveData

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class PostalCodeRepository {
    fun getPostCode(address: String): MutableLiveData<Int> = MutableLiveData(
        when (address) {
            "台北市" -> 100
            else -> -1
        }
    )

}