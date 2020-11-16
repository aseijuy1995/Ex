package edu.yujie.pagingex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class PagingViewModel(private val repo: PagingRepository) : ViewModel() {
    val refreshState = MutableLiveData<Boolean>(false)
    val concertDataSourceFactory = ConcertDataSourceFactory(viewModelScope, repo)
    val concertList: LiveData<PagedList<Concert>> =
        LivePagedListBuilder(
            concertDataSourceFactory, PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()
        ).build()

//    val concertList: LiveData<PagedList<Concert>> =
//        ConcertDataSourceFactory(viewModelScope, repo).toLiveData(
//            20, null
//        )


    fun invalidate() {
        concertDataSourceFactory.dataSourceLiveData.value?.invalidate()
    }
}