package edu.yujie.koinex

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DetailViewModel(repo: DetailRepository) : ViewModel() {
    val mData = repo.getData()

    val data: LiveData<String> = mData

    fun setData(str: String) {
        mData.value = str
    }


}