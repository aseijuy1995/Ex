//package edu.yujie.pagingex.paging3
//
//import androidx.paging.PagingSource
//import edu.yujie.pagingex.constant.Concert
//import edu.yujie.pagingex.constant.PagingRepository
//import kotlinx.coroutines.delay
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
//
//class Concert2PagingSource : PagingSource<Int, Concert>(), KoinComponent {
//    private val TAG = javaClass.simpleName
//    private val repo by inject<PagingRepository>()
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Concert> =
//        try {
//            delay(2000)
//            val fromIndex = params.key ?: 0
//            val count = params.loadSize
//            val toIndex = fromIndex + params.loadSize
//            println("$TAG load: fromIndex:$fromIndex, toIndex:$toIndex")
//
//            repo.load2(fromIndex, toIndex).let {
//                LoadResult.Page(
//                    data = it,
//                    prevKey = null,
//                    nextKey = if (it.size != count) null else toIndex
//                )
//            }
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//
//}