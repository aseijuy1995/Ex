package edu.yujie.hiltex.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Inject


interface WorkService {
    fun init()
}

class WorkServiceImpl @Inject constructor() : WorkService {
    private val TAG = javaClass.simpleName

    override fun init() {
        println("$TAG:init()")
    }
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class WorkServiceModule {
    @Binds
    abstract fun provideWorkService(workServiceImpl: WorkServiceImpl): WorkService
}