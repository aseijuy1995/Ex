package edu.yujie.pagingex.constant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "concert")
data class Concert(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)