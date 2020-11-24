package edu.yujie.hiltex.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteTasksDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalTasksDataSource


interface DataSource {
    fun show()
}

class RemoteDataSource : DataSource {
    private val TAG = javaClass.simpleName
    override fun show() {
        println("$TAG:show()")
    }
}

class LocalDataSource : DataSource {
    private val TAG = javaClass.simpleName
    override fun show() {
        println("$TAG:show()")
    }
}

interface Repository {
    fun showTasks()
}

class TasksRepository @Inject constructor(val dataSource1: DataSource, val dataSource2: DataSource) :
    Repository {
    override fun showTasks() {
        dataSource1.show()
        dataSource2.show()
    }

}

@Module
@InstallIn(ApplicationComponent::class)
object QualifierModule {

    @Singleton
    @RemoteTasksDataSource
    @Provides
    fun provideTasksRemoteDataSource(): DataSource = RemoteDataSource()

    @Singleton
    @LocalTasksDataSource
    @Provides
    fun provideTasksLocalDataSource(): DataSource = LocalDataSource()

    @Singleton
    @Provides
    fun provideTasksRepository(
        @RemoteTasksDataSource dataSource1: DataSource,
        @LocalTasksDataSource dataSource2: DataSource
    ): Repository = TasksRepository(dataSource1, dataSource2)
}