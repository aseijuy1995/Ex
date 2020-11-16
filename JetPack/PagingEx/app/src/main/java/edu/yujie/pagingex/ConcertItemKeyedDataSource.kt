package edu.yujie.pagingex

import androidx.paging.ItemKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConcertItemKeyedDataSource(private val scope: CoroutineScope, private val repo: PagingRepository) : ItemKeyedDataSource<Int, Concert>() {
    private var mPosition = 0

    override fun getKey(item: Concert): Int = item.id

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Concert>) {
        scope.launch {
            delay(3500)
            repo.load(mPosition, params.requestedLoadSize)?.also {
                println("loadInitial = position:$mPosition, params.requestedLoadSize:${params.requestedLoadSize}")
                callback.onResult(it, mPosition, params.requestedLoadSize)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Concert>) {
        scope.launch(Dispatchers.IO) {
            delay(500)
            mPosition += params.requestedLoadSize
            val count = mPosition + params.requestedLoadSize
            repo.load(mPosition, count)?.also {
                println("loadAfter = key:${params.key}, params.requestedLoadSize${params.requestedLoadSize}, position:${mPosition}, count:$count")
                callback.onResult(it)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Concert>) {

    }
}