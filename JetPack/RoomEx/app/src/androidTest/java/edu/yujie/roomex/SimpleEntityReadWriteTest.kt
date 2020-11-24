package edu.yujie.roomex

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * @author YuJie on 2020/11/23
 * @describe 說明
 * @param 參數
 */
@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var myDao: MyDao
    private lateinit var db: AppDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        myDao = db.myDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeThirdAndReadInList() {
//        val thirdList = mutableListOf<Third>()
//        for (i in 0..15) {
//            thirdList.add(Third(
//                id = i,
//                name = "third$i",
//                description = "description${i * 2}",
//                description2 = -1
//            ))
//        }
//        myDao.insertThird(*thirdList.toTypedArray())
//        assertThat
    }
}