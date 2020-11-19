package edu.yujie.pagingex

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import edu.yujie.pagingex.paging2.ConcertDataSourceFactory


class PagingViewModel(private val repo: PagingRepository) : ViewModel() {
    //paging2
    private val factory = ConcertDataSourceFactory(repo, viewModelScope)
    val concertList: LiveData<PagedList<Concert>> =
        LivePagedListBuilder<Int, Concert>(
            factory,
            PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(30)
                .setEnablePlaceholders(true)
                .build()
        ).build()

//    val concertList: LiveData<PagedList<Concert>> =
//        ConcertDataSourceFactory(viewModelScope, repo).toLiveData(
//            20, null
//        )


//    fun invalidate() {
//        concertDataSourceFactory.dataSourceLiveData.value?.invalidate()
//    }
//
//    //paging3
//    fun getConcertData() = repo.getConcertData().asLiveData()


//    fun getConcertFlow() =
//        Pager(PagingConfig(pageSize = 10, initialLoadSize = 20)) {
//            Concert2PagingSource()
//        }.flow.cachedIn(viewModelScope)
}