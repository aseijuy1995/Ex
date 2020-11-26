package edu.yujie.pagingex.paging3

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import edu.yujie.pagingex.ApiService
import edu.yujie.pagingex.constant.Concert
import edu.yujie.pagingex.db.AppDatabase
import edu.yujie.pagingex.db.RemoteKey
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ConcertRemoteMediator : RemoteMediator<Int, Concert>(), KoinComponent {
    private val TAG = javaClass.simpleName
    private val db by inject<AppDatabase>()
    private val service by inject<ApiService>()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Concert>): MediatorResult {
        try {
            val remoteKey: RemoteKey? = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    getRemoteKey(state)
                }
            }

            val fromIndex = remoteKey?.fromIndex ?: 0
            val toIndex = fromIndex + state.config.pageSize
            service.load(fromIndex = fromIndex, toIndex = toIndex).also { concertList ->
                val endOfPaginationReached = concertList.isEmpty()

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.concertDao.deleteAll()
                        remoteKey?.let { db.remoteKeyDao.deleteById(it.id) }
                    }

                    val index = fromIndex + concertList.size
                    println("$TAG: fromIndex:$fromIndex, concertList.size:${concertList.size}, toIndex:$toIndex")

                    concertList.lastOrNull()?.let {
                        val remoteKey = RemoteKey(id = it.id, fromIndex = index)
                        db.concertDao.insertConcerts(concertList)
                        db.remoteKeyDao.insertRemoteKey(remoteKey)
                    }
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKey(state: PagingState<Int, Concert>): RemoteKey? =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            println("$TAG:getRemoteKey: id = ${it.id}")
            db.remoteKeyDao.queryRemoteKeyById(it.id)
        }
}