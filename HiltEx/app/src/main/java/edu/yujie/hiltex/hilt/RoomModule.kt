package edu.yujie.hiltex.hilt

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import edu.yujie.hiltex.room.AppDao
import edu.yujie.hiltex.room.AppResultDataBase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(
        application: Application
    ): AppResultDataBase =
        Room.databaseBuilder(application, AppResultDataBase::class.java, "hiltEx.db").build()

    @Provides
    @Singleton
    fun provideAppDao(appResultDatabase: AppResultDataBase): AppDao = appResultDatabase.appDao

}