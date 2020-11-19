package edu.yujie.pagingex.paging3

//import androidx.paging.PagingSource
import edu.yujie.pagingex.Concert
import edu.yujie.pagingex.PagingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

//class ConcertPagingSource(private val repo: PagingRepository) : PagingSource<Int, Concert>(), CoroutineScope {
//    private var mPosition = 0
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Concert> =
//        try {
//            coroutineScope {
//                delay(1000)
//                mPosition = params.key ?: 0
//                val toIndex = mPosition + params.loadSize
//                repo.load(mPosition, toIndex).let {
//                    println("load = mPosition:$mPosition, toIndex:$toIndex, params.loadSize:${params.loadSize}, list.size:${it?.size}. params.pageSize:${params.pageSize}")
//                    LoadResult.Page(
//                        it,
//                        null,
//                        if (toIndex < 500) toIndex else null
//                    )
//                }
//            }
//        } catch (e: Exception) {
//            LoadResult.Error<Int, Concert>(e)
//        }
//
//    override val coroutineContext: CoroutineContext
//        get() = Job()
//}