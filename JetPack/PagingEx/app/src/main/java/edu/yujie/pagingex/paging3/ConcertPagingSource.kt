package edu.yujie.pagingex.paging3

import androidx.paging.PagingSource
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.constant.PagingRepository
import edu.yujie.pagingex.db.ConcertDao
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.experimental.property.inject
import org.koin.java.KoinJavaComponent.inject
import retrofit2.HttpException
import java.io.IOException

class ConcertPagingSource : PagingSource<Int, Concert>() ,KoinComponent{
    private val repo by inject<PagingRepository>()
    private val TAG = javaClass.simpleName

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Concert> =
        try {
            delay(1500)
            val fromIndex = params.key ?: 0
            val toIndex = fromIndex + params.loadSize
            println("$TAG:, fromIndex:$fromIndex, toIndex:$toIndex")
            //remote
            repo.load(fromIndex, toIndex).let {
                println("$TAG: list: ${it}")
                LoadResult.Page<Int, Concert>(
                    it,
                    prevKey = null,
                    nextKey = toIndex
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            println("$TAG: IOException")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            e.printStackTrace()
            println("$TAG: HttpException")
            LoadResult.Error(e)
        }
}