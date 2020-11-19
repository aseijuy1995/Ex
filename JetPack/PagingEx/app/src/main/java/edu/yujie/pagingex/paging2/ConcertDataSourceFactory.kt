package edu.yujie.pagingex.paging2

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import edu.yujie.pagingex.Concert
import edu.yujie.pagingex.PagingRepository
import kotlinx.coroutines.CoroutineScope

class ConcertDataSourceFactory(private val repo: PagingRepository, private val scope: CoroutineScope) : DataSource.Factory<Int, Concert>() {
    val dataSourceLiveData = MutableLiveData<ConcertItemKeyedDataSource>()

    override fun create(): DataSource<Int, Concert> {
        val dataSource = ConcertItemKeyedDataSource(repo, scope)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}