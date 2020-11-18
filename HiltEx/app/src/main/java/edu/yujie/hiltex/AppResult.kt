package edu.yujie.hiltex

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appResult")
data class AppResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "applestoreurl") val applestoreurl: String? = "",
    @ColumnInfo(name = "result") val result: String? = ""
)