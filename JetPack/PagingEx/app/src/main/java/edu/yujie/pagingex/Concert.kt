package edu.yujie.pagingex

import androidx.room.Entity

@Entity(tableName = "concert")
data class Concert(val id: Int, val name: String)