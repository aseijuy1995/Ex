package edu.yujie.hiltex.arch

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import edu.yujie.hiltex.ApiService
import edu.yujie.hiltex.AppResult
import edu.yujie.hiltex.room.AppDao
import javax.inject.Inject

class HiltRepository @Inject constructor() {
    @Inject
    lateinit var service: ApiService

    @Inject
    lateinit var dao: AppDao

    suspend fun postAppResult(): AppResult = service.postAppResult()

    suspend fun insert(): Long {
        val appResult = postAppResult()
        return dao.insert(appResult)
    }

    //viewModel & room - from database
    fun getAppResult(): LiveData<AppResult> = dao.getAppResult().asLiveData()
}