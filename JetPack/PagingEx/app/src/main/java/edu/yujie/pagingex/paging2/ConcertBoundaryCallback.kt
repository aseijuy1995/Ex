package edu.yujie.pagingex.paging2

import androidx.paging.PagedList
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.constant.PagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConcertBoundaryCallback(private val repo: PagingRepository, private val scope: CoroutineScope) : PagedList.BoundaryCallback<Concert>() {
    private val TAG = javaClass.simpleName
    var fromIndex = 0
    var toIndex = fromIndex + 20

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        println("$TAG: onZeroItemsLoaded")
        scope.launch(Dispatchers.IO) {
            delay(1500)
            repo.load(fromIndex, toIndex).also {
                fromIndex = toIndex
                toIndex += it.size
                concertList.postValue(it)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Concert) {
        super.onItemAtEndLoaded(itemAtEnd)
        println("$TAG: onItemAtEndLoaded")
        scope.launch(Dispatchers.IO) {
            delay(500)
            repo.load(fromIndex, toIndex).also {
                fromIndex = toIndex
                toIndex += it.size
                concertList.postValue(it)
            }
        }
    }
}