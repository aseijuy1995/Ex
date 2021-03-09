package tw.north27.coachingapp.repository

import androidx.paging.Pager
import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.ext.pagingConfig
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.module.ext.safeApiResults
import tw.north27.coachingapp.module.http.Results
import tw.north27.coachingapp.page.NotifyPagingSource
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyRepository(private val service: IApiService) : INotifyRepository {

    override fun loadNotify(): Pager<Int, NotifyInfo> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { NotifyPagingSource(service) }
        )
    }

    override suspend fun switchNotify(isOpen: Boolean): Results<Boolean> {
        return safeApiResults { service.postSwitchNotify(isOpen) }
    }

    override suspend fun readAllNotify(): Results<Boolean> {
        return safeApiResults { service.postReadAllNotify() }
    }

    override suspend fun deleteNotify(notifyId: Long): Results<Boolean> {
        return safeApiResults { service.postDeleteNotify(notifyId) }
    }
}