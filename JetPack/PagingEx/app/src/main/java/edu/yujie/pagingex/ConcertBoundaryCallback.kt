package edu.yujie.pagingex

import androidx.paging.PagedList

class ConcertBoundaryCallback(private val repo: PagingRepository) : PagedList.BoundaryCallback<Concert>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        repo.loadMore()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Concert) {
        super.onItemAtEndLoaded(itemAtEnd)
    }

    override fun onItemAtFrontLoaded(itemAtFront: Concert) {
        super.onItemAtFrontLoaded(itemAtFront)
    }
}