package edu.yujie.pagingex

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ConcertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg concerts: Concert)

    @Query("select * from concert")
    fun pagingSource(): PagingSource<Int, Concert>

    @Delete
    suspend fun clearAll()
}