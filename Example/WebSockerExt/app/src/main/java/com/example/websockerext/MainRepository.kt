package com.example.websockerext

import com.example.websockerext.conn.IApiService

interface MainRepository {
    suspend fun postAppResult():AppResult
}

class MainRepositoryImpl(private val iApiService: IApiService) : MainRepository {
    override suspend fun postAppResult()  = iApiService.postAppResult()

}