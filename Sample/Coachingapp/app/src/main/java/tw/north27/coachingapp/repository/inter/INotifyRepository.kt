package tw.north27.coachingapp.repository.inter

import androidx.paging.Pager
import tw.north27.coachingapp.model.result.NotifyInfo

interface INotifyRepository {

    fun postLoadNotifyPager(page: Int): Pager<Int, NotifyInfo>

}