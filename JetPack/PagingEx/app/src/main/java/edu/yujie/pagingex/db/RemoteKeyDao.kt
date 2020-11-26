package edu.yujie.pagingex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(key: RemoteKey)

    @Query("select * from remote_key where id = :id")
    suspend fun queryRemoteKeyById(id: Int): RemoteKey

    @Query("delete from remote_key where id = :id")
    suspend fun deleteById(id: Int)
}