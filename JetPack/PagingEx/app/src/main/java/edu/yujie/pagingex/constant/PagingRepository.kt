package edu.yujie.pagingex.constant

//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.cachedIn

class PagingRepository(
//    private val dao: ConcertDao
) {
    private val TAG = javaClass.simpleName
    private val list = mutableListOf<Concert>()

    init {
        for (i in 1..197 step 2) {
            val concert = Concert(id = i, name = "Name = $i")
            list.add(concert)
        }
    }

    //paging2
//    fun load(): LiveData<PagedList<Concert>> = dao.getConcertList().toLiveData(pageSize = 20)

    fun load(fromIndex: Int, toIndex: Int): List<Concert> =
        try {
            println("$TAG:load = try")
            list.subList(fromIndex, toIndex)
        } catch (e: Exception) {
            if (fromIndex >= list.size && toIndex >= list.size)
                emptyList<Concert>()
            else
                list.subList(fromIndex, list.size)
        }


//    fun loadMore() {
//        for (i in 1001..2000 step 3) {
//            val concert = Concert(id = i, name = "More:Name = $i")
//            list.add(concert)
//        }
//    }

    //paging3
//    fun getConcertData() = Pager(PagingConfig(pageSize = 20, initialLoadSize = 20)) {
//        ConcertPagingSource(this)
//    }.flow

    //
//    fun load2(fromIndex: Int, toIndex: Int): List<Concert> = when {
//        fromIndex > list.size -> emptyList()
//        toIndex >= list.size - 1 -> list.subList(fromIndex, list.size)
//        else -> list.subList(fromIndex, toIndex)
//    }

}