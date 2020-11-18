package edu.yujie.pagingex

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import edu.yujie.pagingex.paging3.Concert2PagingSource
import edu.yujie.pagingex.paging3.ConcertPagingSource

class PagingRepository {
    private val list = mutableListOf<Concert>()

    init {
        //data
        for (i in 1..1000 step 2) {
            val concert = Concert(id = i, name = "Name = $i")
            list.add(concert)
        }
    }

    fun load(position: Int, toIndex: Int): List<Concert> =
        when {
            position >= list.size - 1 -> emptyList()
            toIndex > list.size -> list.subList(position, list.size)
            else -> list.subList(position, toIndex)
        }

    fun loadMore() {
        for (i in 1001..2000 step 3) {
            val concert = Concert(id = i, name = "More:Name = $i")
            list.add(concert)
        }
    }

    //paging3
    fun getConcertData() = Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
        ConcertPagingSource(this)
    }.flow

    //
    fun load2(fromIndex: Int, toIndex: Int): List<Concert> = when {
        fromIndex > list.size -> emptyList()
        toIndex >= list.size - 1 -> list.subList(fromIndex, list.size)
        else -> list.subList(fromIndex, toIndex)
    }

}