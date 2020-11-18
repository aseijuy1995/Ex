package edu.yujie.hiltex.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.yujie.hiltex.AppResult
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(appResult: AppResult): Long

    @Query("SELECT * FROM appResult")
    fun getAppResult(): Flow<AppResult>
}