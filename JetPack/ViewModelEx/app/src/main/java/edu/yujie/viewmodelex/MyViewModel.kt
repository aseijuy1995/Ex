package edu.yujie.viewmodelex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author YuJie on 2020/10/18
 * @describe 說明
 * @param 參數
 */
class MyViewModel(val repo: MyRepository) : ViewModel() {
    private val mUsers: MutableLiveData<List<String>> by lazy {
        repo.users
    }
    val users: LiveData<List<String>> = mUsers
}

object MyViewModelFactory : ViewModelProvider.Factory {
    val repo = MyRepository()
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java))
            return MyViewModel(repo) as T
        else
            throw Exception("Can not create viewModel")
    }

}