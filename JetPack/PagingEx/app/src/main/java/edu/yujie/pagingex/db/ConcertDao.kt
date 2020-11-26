package edu.yujie.pagingex.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.yujie.pagingex.constant.Concert

@Dao
interface ConcertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConcerts(concerts: List<Concert>)

    @Query("delete from concert")
    fun deleteAll()

    @Query("select * from concert order by id asc")
    fun queryConcerts(): PagingSource<Int, Concert>


}