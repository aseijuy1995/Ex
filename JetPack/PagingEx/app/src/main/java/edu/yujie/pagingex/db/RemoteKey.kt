package edu.yujie.pagingex.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val id: Int,
    val fromIndex: Int?
)