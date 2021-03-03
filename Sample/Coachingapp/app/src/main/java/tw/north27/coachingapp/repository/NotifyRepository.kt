package tw.north27.coachingapp.repository

import androidx.paging.Pager
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext.pagingConfig
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.page.NotifyPagingSource
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyRepository(private val service: IApiService) : INotifyRepository {

    override fun postLoadNotifyPager(page: Int): Pager<Int, NotifyInfo> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { NotifyPagingSource(service) }
        )
    }
}