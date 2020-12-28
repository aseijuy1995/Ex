package edu.yujie.mviex

import edu.yujie.mviex.bean.RepoBean
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {

    @GET("/search/repositories")
    suspend fun searchRepo(@Query("q") query: String): RepoBean

}