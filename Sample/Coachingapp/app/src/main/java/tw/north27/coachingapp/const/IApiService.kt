package tw.north27.coachingapp.const


import retrofit2.http.GET
import tw.north27.coachingapp.model.Data
import tw.north27.coachingapp.model.OriginateResult

interface IApiService {

    @GET
    suspend fun getOriginateData(): OriginateResult


    @GET("todos/1")
    suspend fun getData(): Data


}