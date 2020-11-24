package edu.yujie.pagingex.constant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import edu.yujie.pagingex.db.AppDatabase
import edu.yujie.pagingex.paging2.ConcertBoundaryCallback
import edu.yujie.pagingex.paging2.ConcertDataSourceFactory
import edu.yujie.pagingex.paging3.ConcertRemoteMediator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PagingViewModel(private val repo: PagingRepository) : ViewModel(), KoinComponent {
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
    private val db by inject<AppDatabase>()
    private val concertDao = db.concertDao
    val concertListFlow = Pager<Int, Concert>(
        PagingConfig(pageSize = 20, initialLoadSize = 20),
        remoteMediator = ConcertRemoteMediator("")
    ) {
        //remote
//        ConcertPagingSource()
        //local || local + remote
        concertDao.getConcertList()
    }.flow.cachedIn(viewModelScope)

}