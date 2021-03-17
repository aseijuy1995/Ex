package tw.north27.coachingapp.notify

import androidx.paging.Pager
import tw.north27.coachingapp.model.result.NotifyInfo
import tw.north27.coachingapp.module.http.Results

interface INotifyRepository {

    fun loadNotify(): Pager<Int, NotifyInfo>

    suspend fun switchNotify(isOpen: Boolean): Results<Boolean>

    suspend fun readAllNotify(): Results<Boolean>

    suspend fun deleteNotify(notify: NotifyInfo): Results<Boolean>


}