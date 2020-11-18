package edu.yujie.hiltex.room

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.yujie.hiltex.AppResult


@Database(entities = [AppResult::class], version = 1)
abstract class AppResultDataBase : RoomDatabase() {

    abstract val appDao: AppDao
}