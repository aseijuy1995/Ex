package edu.yujie.roomex

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * @author YuJie on 2020/11/23
 * @describe 說明
 * @param 參數
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val testDB = "db_name"

    @Rule
    @JvmField
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migration5_6() {
        helper.createDatabase(testDB, 5).apply {
            execSQL("create table third_new (id integer not null, name text not null, description text not null,description2 integer not null, primary key(id))")//new
            execSQL("insert into third_new(id, name, description, description2) select id, name, description, description2 from third")//copy
            execSQL("drop table third")//delete
            execSQL("alter table third_new rename to third")//delete
            close()
        }
        helper.runMigrationsAndValidate(testDB, 6, true, AppDatabase.MIGRATION5_6)
    }
}