//package tw.north27.coachingapp.notify
//
//import androidx.paging.Pager
//import tw.north27.coachingapp.consts.IApiService
//import tw.north27.coachingapp.ext2.pagingConfig
//import tw.north27.coachingapp.model.result.NotifyInfo
//import tw.north27.coachingapp.ext2.safeApiResults
//
//class NotifyRepository(private val service: IApiService) : INotifyRepository {
//
//    override fun loadNotify(): Pager<Int, NotifyInfo> {
//        return Pager(
//            config = pagingConfig,
//            pagingSourceFactory = { NotifyPagingSource(service) }
//        )
//    }
//
//    override suspend fun switchNotify(isOpen: Boolean): Results<Boolean> {
//        return safeApiResults { service.postSwitchNotify(isOpen) }
//    }
//
//    override suspend fun readAllNotify(): Results<Boolean> {
//        return safeApiResults { service.postReadAllNotify() }
//    }
//
//    override suspend fun deleteNotify(notify: NotifyInfo): Results<Boolean> {
//        return safeApiResults { service.deleteNotify(notify) }
//    }
//}