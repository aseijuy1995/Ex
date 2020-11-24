package edu.yujie.koinex

import androidx.lifecycle.MutableLiveData

class DetailRepository {

    fun getData(): MutableLiveData<String> = MutableLiveData<String>("Koin")
}