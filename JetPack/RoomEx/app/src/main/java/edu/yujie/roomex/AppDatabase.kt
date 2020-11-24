package edu.yujie.roomex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author YuJie on 2020/11/15
 * @describe 說明
 * @param 參數
 */
@Database(entities = [User::class, First::class, Second::class, Third::class
//    , Library::class, Play::class, Song::class
],
    views = [FirstSecond::class],
    version = 6)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val myDao: MyDao

    companion object {
        @Volatile
        private var appDatabase: AppDatabase? = null

        val MIGRATION3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table third_new (id integer, name text, description text,description2 text, primary key(id))")//new
                database.execSQL("insert into third_new(id, name, description, description2) select id, name, description, description2 from third")//copy
                database.execSQL("drop table third")//delete
                database.execSQL("alter table third_new rename to third")//delete
            }
        }

        val MIGRATION4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table third_new (id integer, name text, description text,description2 integer, primary key(id))")//new
                database.execSQL("insert into third_new(id, name, description, description2) select id, name, description, description2 from third")//copy
                database.execSQL("drop table third")//delete
                database.execSQL("alter table third_new rename to third")//delete
            }
        }

        val MIGRATION5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table third_new (id integer not null, name text not null, description text not null,description2 integer not null, primary key(id))")//new
                database.execSQL("insert into third_new(id, name, description, description2) select id, name, description, description2 from third")//copy
                database.execSQL("drop table third")//delete
                database.execSQL("alter table third_new rename to third")//delete
            }
        }

        fun get(context: Context, scope: CoroutineScope): AppDatabase =
            appDatabase ?: synchronized(this) {
                appDatabase ?: buildDatabase(context, scope).also {
                    appDatabase = it
                }
            }

        private fun buildDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "db_name")
//            .createFromAsset("database/myapp.db")
//            .createFromFile(File("mypath"))
                .addCallback(MyDatabaseCallback(context, scope))
//                .fallbackToDestructiveMigration()
//                .fallbackToDestructiveMigrationFrom()
//                .fallbackToDestructiveMigrationOnDowngrade()
                .addMigrations(MIGRATION3_4, MIGRATION4_5, MIGRATION5_6)
                .build()
        }
    }


    class MyDatabaseCallback(private val context: Context, private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch(Dispatchers.IO) {
                val appDatabase = AppDatabase.get(context, scope)
                val myDao = appDatabase.myDao
                val firstList = mutableListOf<First>()
                for (i in 0..10) {
                    firstList.add(First(id = i, name = "first$i", description = "description$i"))
                }
                myDao.insertFirst(*firstList.toTypedArray())
                val secondList = mutableListOf<Second>()
                for (i in 0..10) {
                    secondList.add(Second(id = i,
                        name = "second$i",
                        description = "description${i * 2}"))
                }
                myDao.insertSecond(*secondList.toTypedArray())
            }
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
//            scope.launch(Dispatchers.IO) {
//                val appDatabase = AppDatabase.get(context, scope)
//                val myDao = appDatabase.myDao
//                val thirdList = mutableListOf<Third>()
//                for (i in 0..15) {
//                    thirdList.add(Third(
//                        id = i,
//                        name = "third$i",
//                        description = "description${i * 2}",
//                        description2 = -1
//                    ))
//                }
//                myDao.insertThird(*thirdList.toTypedArray())
//                println("insertThird")
//            }
        }
    }
}