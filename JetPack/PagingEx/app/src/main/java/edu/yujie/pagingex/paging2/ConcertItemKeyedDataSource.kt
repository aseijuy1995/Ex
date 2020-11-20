package edu.yujie.pagingex.paging2

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.constant.PagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val concertList = MutableLiveData<List<Concert>>()

class ConcertItemKeyedDataSource(private val repo: PagingRepository, private val scope: CoroutineScope) : ItemKeyedDataSource<Int, Concert>() {
    private val TAG = javaClass.simpleName
    private var mPosition = 0
    private val mTotalCount = 10000

    override fun getKey(item: Concert): Int = item.id

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Concert>) {
        scope.launch(Dispatchers.IO) {
            delay(1500)
            val fromIndex = mPosition
            val toIndex = mPosition + params.requestedLoadSize
            println("$TAG:loadInitial = fromIndex:$fromIndex, toIndex:$toIndex")
            repo.load(fromIndex, toIndex).also {
                callback.onResult(it, mPosition, mTotalCount)
                mPosition = toIndex
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Concert>) {
        scope.launch(Dispatchers.IO) {
            delay(500)
            val fromIndex = mPosition
            val toIndex = mPosition + params.requestedLoadSize
            println("$TAG:loadAfter = fromIndex:$fromIndex, toIndex:$toIndex")
            repo.load(fromIndex, toIndex).also {
                callback.onResult(it)
                mPosition = toIndex
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Concert>) {

    }

}