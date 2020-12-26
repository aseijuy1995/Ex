package edu.yujie.mvvmex

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.yujie.mvvmex.bean.RepoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepo) : ViewModel() {
    private val mRepoLiveData: MutableLiveData<RepoBean> by lazy { MutableLiveData<RepoBean>() }

    val repoLiveData: LiveData<RepoBean> = mRepoLiveData

    private val mIsEmptyLiveData: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val isEmptyLiveData: LiveData<Boolean> = mIsEmptyLiveData

    private val mSearchKeyWork: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val searchKeyWork: LiveData<String> = mSearchKeyWork


    fun searchRepo(text: String) = viewModelScope.launch(Dispatchers.IO) {
        val repo = repo.searchRepo(text)
        mRepoLiveData.postValue(repo)
    }

    fun checkEmpty(text: String?) {
        mSearchKeyWork.postValue(text)
        if (TextUtils.isEmpty(text))
            mIsEmptyLiveData.postValue(true)
        else
            mIsEmptyLiveData.postValue(false)

    }


}