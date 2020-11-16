package edu.yujie.pagingex

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

class ConcertDataSourceFactory(private val scope: CoroutineScope, private val repo: PagingRepository) : DataSource.Factory<Int, Concert>() {
    val dataSourceLiveData = MutableLiveData<ConcertItemKeyedDataSource>()

    override fun create(): DataSource<Int, Concert> {
        val dataSource = ConcertItemKeyedDataSource(scope, repo)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}