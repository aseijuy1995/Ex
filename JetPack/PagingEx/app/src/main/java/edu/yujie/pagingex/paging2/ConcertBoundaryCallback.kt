package edu.yujie.pagingex.paging2

import androidx.paging.PagedList
import edu.yujie.pagingex.Concert
import edu.yujie.pagingex.PagingRepository

class ConcertBoundaryCallback(private val repo: PagingRepository) : PagedList.BoundaryCallback<Concert>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
//        repo.loadMore()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Concert) {
        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Concert) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}