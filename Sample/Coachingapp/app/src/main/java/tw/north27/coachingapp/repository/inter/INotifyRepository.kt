package tw.north27.coachingapp.repository.inter

import tw.north27.coachingapp.model.result.NotifyInfo

interface INotifyRepository {

    suspend fun postNotifyList(fromIndex:Int, toIndex:Int): List<NotifyInfo>

}