package edu.yujie.pagingex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.yujie.pagingex.constant.Concert

@Database(entities = [Concert::class, RemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val concertDao: ConcertDao

    abstract val remoteKeyDao: RemoteKeyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "appdatabase")
                    .addCallback(AppDatabaseCallback())
                    .build().also {
                        instance = it
                    }
            }
    }

    class AppDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

        }
    }
}