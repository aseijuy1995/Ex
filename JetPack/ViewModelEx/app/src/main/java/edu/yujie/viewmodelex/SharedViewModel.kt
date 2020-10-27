package edu.yujie.viewmodelex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class SharedViewModel : ViewModel() {
    private val mSelected = MutableLiveData<Int>(0)

    val selected: LiveData<Int> = mSelected

    fun setSelect(select: Int) = mSelected.postValue(select)
}