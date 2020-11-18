package edu.yujie.pagingex

import androidx.lifecycle.*
import androidx.paging.*
import edu.yujie.pagingex.paging2.ConcertDataSourceFactory
import edu.yujie.pagingex.paging3.Concert2PagingSource

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

    //paging3
    fun getConcertData() = repo.getConcertData().asLiveData()


    fun getConcertFlow() =
        Pager(PagingConfig(pageSize = 10, initialLoadSize = 20)) {
            Concert2PagingSource()
        }.flow.cachedIn(viewModelScope)
}