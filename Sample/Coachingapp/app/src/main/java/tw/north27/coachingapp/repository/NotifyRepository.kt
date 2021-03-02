package tw.north27.coachingapp.repository

import tw.north27.coachingapp.consts.IApiService
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.repository.inter.INotifyRepository

class NotifyRepository(private val service: IApiService) : INotifyRepository {

    override suspend fun postNotifyList(fromIndex: Int, toIndex: Int): List<NotifyInfo> {
        return service.postNotifyList(fromIndex, toIndex)
    }
}