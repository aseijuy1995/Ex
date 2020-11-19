import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.yujie.pagingex.Concert

@Dao
interface ConcertDao {

    @Query("select * from concert order by id collate nocase ASC")
    fun getConcertList(): DataSource.Factory<Int, Concert>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend
     fun insertAll(vararg concerts: Concert)

}