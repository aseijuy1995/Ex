package edu.yujie.retrofitex

import retrofit2.http.GET

interface IApiService2 {
    @GET(value = "posts")
    suspend fun getOtherGithubData(): List<OtherGithubBean>

}