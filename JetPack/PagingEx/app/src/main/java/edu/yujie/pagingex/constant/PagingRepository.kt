package edu.yujie.pagingex.constant

import androidx.paging.Pager
import androidx.paging.PagingConfig
import edu.yujie.pagingex.db.AppDatabase
import edu.yujie.pagingex.paging3.ConcertRemoteMediator

class PagingRepository(private val db: AppDatabase) {
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

    //paging3
    val concertListFlow =
        Pager<Int, Concert>(
            PagingConfig(
                pageSize = 20,
//                prefetchDistance = 1,
                enablePlaceholders = true
            ),
            remoteMediator = ConcertRemoteMediator()
        ) {
            //remote
//            ConcertPagingSource()
            //local || local + remote
            db.concertDao.queryConcerts()
        }.flow

}