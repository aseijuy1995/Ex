package edu.yujie.pagingex

import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {

    abstract val concertDao: ConcertDao

}