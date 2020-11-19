//package edu.yujie.pagingex
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//
//@OptIn(ExperimentalPagingApi::class)
//class ConcertRemoteMediator(private val db: AppDatabase, private val apiService: ApiService) : RemoteMediator<Int, Concert>() {
//    private val TAG = javaClass.simpleName
//
//    override suspend fun load(loadType: LoadType, state: PagingState<Int, Concert>): MediatorResult {
//        try {
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> null
//                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//                    lastItem.id
//                }
//            }
//
//            val page = loadKey ?: 0
//            println("$TAG:load = pageSize:${state.config.pageSize}, page + pageSize:${page + state.config.pageSize}")
//            val list = apiService.load(state.config.pageSize, page + state.config.pageSize)
//
//            db.withTransaction {
//                when (loadType) {
//                    LoadType.REFRESH -> {
//                        db.concertDao.clearAll()
//                    }
//                }
//                val nextKey = if (state.config.pageSize > list.size) null else page + state.config.pageSize
//
//                db.concertDao.insertAll(*list.toTypedArray())
//            }
//
//        } catch (e: Exception) {
//            return MediatorResult.Error(e)
//        }
//    }
//}