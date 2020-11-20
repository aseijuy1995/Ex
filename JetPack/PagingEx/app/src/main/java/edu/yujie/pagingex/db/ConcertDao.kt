package edu.yujie.pagingex.db

import androidx.paging.PagingSource
import androidx.room.*
import edu.yujie.pagingex.constant.Concert

@Dao
interface ConcertDao {

    @Query("select * from concert")
    fun getConcertList(): PagingSource<Int, Concert>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConcertList(vararg concerts: Concert)

    @Delete
    fun deleteConcertList(vararg concerts: Concert)
}