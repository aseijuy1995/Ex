package edu.yujie.pagingex.paging2

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import edu.yujie.pagingex.Concert
import edu.yujie.pagingex.PagingRepository
import kotlinx.coroutines.CoroutineScope

class ConcertDataSourceFactory(private val scope: CoroutineScope, private val repo: PagingRepository) : DataSource.Factory<Int, Concert>() {
    val dataSourceLiveData = MutableLiveData<ConcertItemKeyedDataSource>()

    override fun create(): DataSource<Int, Concert> {
        val dataSource = ConcertItemKeyedDataSource(scope, repo)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}