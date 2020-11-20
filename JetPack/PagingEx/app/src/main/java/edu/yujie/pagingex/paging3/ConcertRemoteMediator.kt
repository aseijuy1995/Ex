package edu.yujie.pagingex.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.constant.PagingRepository
import edu.yujie.pagingex.db.AppDatabase
import edu.yujie.pagingex.db.ConcertDao
import edu.yujie.pagingex.db.RemoteKeyDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ConcertRemoteMediator(
    private val query: String
) : RemoteMediator<Int, Concert>(), KoinComponent {
    private val TAG = javaClass.simpleName
    private val db by inject<AppDatabase>()
    private val concertDao: ConcertDao = db.concertDao
    private val remoteKeyDao: RemoteKeyDao = db.remoteKeyDao
    private val repo by inject<PagingRepository>()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Concert>): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    lastItem.id


//                    val remoteKey = db.withTransaction {
//                        remoteKeyDao.queryRemoteKey(query)
//                    }
//
//                    if (remoteKey.nextKey == null) {
//                        return MediatorResult.Success(endOfPaginationReached = true)
//                    }
//                    remoteKey.nextKey
                }




                
            }

//            val concertList = repo.load()

            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

//    override suspend fun load(loadType: LoadType, state: PagingState<Int, Concert>): MediatorResult {
//        try {
//            val loadKey = when (loadType) {
//                LoadType.REFRESH -> 0
//                LoadType.PREPEND -> {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
//                    lastItem.id
//                }
//            }
//
//            println("$TAG: loadKey:$loadKey")
//            repo.load(0, loadKey).also {
//                db.withTransaction {
//                    if (loadType == LoadType.REFRESH) {
//                        concertDao.deleteConcertList()
//                    }
//                    concertDao.insertConcertList(*it.toTypedArray())
//                }
//            }
//            return MediatorResult.Success(endOfPaginationReached = true)
//        } catch (e: IOException) {
//            return MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            return MediatorResult.Error(e)
//        }
//    }

}