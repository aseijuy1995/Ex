package edu.yujie.pagingex.paging2

import ConcertDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.yujie.pagingex.Concert

@Database(entities = [Concert::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun concertDao(): ConcertDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "concertDB")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<PagingDatabaseWorker>().build()
//                            WorkManager.getInstance(context).enqueue(request)
                            insertData(context.applicationContext)
                        }
                    })
                    .build().also {
                        instance = it
                    }
            }

        private fun insertData(context: Context) {
            val list = mutableListOf<Concert>()
            for (i in 1..197 step 2) {
                val concert = Concert(id = i, name = "Name = $i")
                list.add(concert)
            }
            get(context).concertDao().insertAll(*list.toTypedArray())
        }
    }
}