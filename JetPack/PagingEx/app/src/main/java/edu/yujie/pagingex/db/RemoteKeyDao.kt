package edu.yujie.pagingex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("select * from remote_key where label = :query")
    suspend fun queryRemoteKey(query: String): RemoteKey

    @Query("delete from remote_key where label = :query")
    suspend fun deleteByQuery(query: String)
}