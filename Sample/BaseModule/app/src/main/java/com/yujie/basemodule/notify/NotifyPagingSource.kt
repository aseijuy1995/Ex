package tw.north27.coachingapp.notify

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.NotifyInfo
import java.io.IOException

/**
 * PageNumber 頁碼
 * */
class NotifyPagingSource(private val service: IApiService) : PagingSource<Int, NotifyInfo>() {

    override fun getRefreshKey(state: PagingState<Int, NotifyInfo>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotifyInfo> {
        return try {
            val pageNumber = params.key ?: 1
            val notifyInfoList = service.getLoadNotify(pageNumber)
            LoadResult.Page<Int, NotifyInfo>(
                data = notifyInfoList,
                prevKey = null,
                nextKey = if (notifyInfoList.isEmpty()) null else pageNumber + 1,//控制nextPage頁碼
            )
        } catch (e: IOException) {
            LoadResult.Error(e)

        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}