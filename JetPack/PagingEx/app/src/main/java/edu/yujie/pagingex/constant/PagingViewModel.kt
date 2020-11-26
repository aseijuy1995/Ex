package edu.yujie.pagingex.constant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.cachedIn
import edu.yujie.pagingex.paging2.ConcertBoundaryCallback
import edu.yujie.pagingex.paging2.ConcertDataSourceFactory


class PagingViewModel(private val repo: PagingRepository) : ViewModel() {
    //paging2
    private val mIsRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> = mIsRefresh
    private val factory = ConcertDataSourceFactory(repo, viewModelScope)
    private val boundaryCallback = ConcertBoundaryCallback(repo, viewModelScope)
    val concertList: LiveData<PagedList<Concert>> =
//        factory.toLiveData(pageSize = 20)
        LivePagedListBuilder<Int, Concert>(
            factory,
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(3)
                .setPageSize(5)
                .setEnablePlaceholders(true)
                .build()
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

    fun setRefresh(state: Boolean) = mIsRefresh.postValue(state)

    fun invalidateDataSource() {
        setRefresh(true)
        factory.dataSourceLiveData.value?.invalidate()
    }

    //paging3
    val concertListFlow = repo.concertListFlow.cachedIn(viewModelScope)

}