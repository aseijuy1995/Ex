package edu.yujie.livedataex

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MainViewModel(private val repo: PostalCodeRepository) : ViewModel() {
    private val mCurrentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val currentName: LiveData<String> = mCurrentName

    fun setCurrentName(name: String) {
        mCurrentName.value = name
    }

    //
    private val mNum by lazy {
        MutableLiveData<Int>(123)
    }
    val num: LiveData<Int> = mNum

    //map
    val numToString = num.map {
        it.toString()
    }

    //switchMap
    val numToStringLiveData = num.switchMap {
        MutableLiveData(it.toString())
    }

    //
    fun getPostCode(address: String): LiveData<Int> = repo.getPostCode(address)
}

object MainViewModelFactory : ViewModelProvider.Factory {
    private val repo = PostalCodeRepository()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repo) as T
        else
            throw Exception("Can not create viewModel")
    }

}