package edu.yujie.pagingex.paging2

import androidx.paging.PositionalDataSource
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.constant.PagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConcertPositionalDataSource(private val repo: PagingRepository, private val scope: CoroutineScope) : PositionalDataSource<Concert>() {
    private val TAG = javaClass.simpleName

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Concert>) {
        scope.launch(Dispatchers.IO) {
            delay(1500)
            println("$TAG: params.requestedStartPosition:${params.requestedStartPosition}, params.requestedLoadSize:${params.requestedLoadSize}, params.pageSize:${params.pageSize}")
            repo.load(params.requestedStartPosition, params.requestedLoadSize).also {
                callback.onResult(it, params.requestedStartPosition, 10000)
//                callback.onResult(it, 0, 0)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Concert>) {
        scope.launch(Dispatchers.IO) {
            delay(500)
            println("$TAG: params.startPosition:${params.startPosition}, params.loadSize:${params.loadSize}")
            val toIndex = params.startPosition + params.loadSize
            repo.load(params.startPosition, toIndex).also {
                callback.onResult(it)
            }
        }
    }

}