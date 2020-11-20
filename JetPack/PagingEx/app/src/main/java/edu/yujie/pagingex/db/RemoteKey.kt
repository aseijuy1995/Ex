package edu.yujie.pagingex.db

import androidx.room.Entity

@Entity(tableName = "remote_key")
data class RemoteKey(
    val label: String,
    val nextKey: String?
)