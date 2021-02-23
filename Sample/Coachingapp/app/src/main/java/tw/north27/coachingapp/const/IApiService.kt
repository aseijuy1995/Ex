package tw.north27.coachingapp.const


import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tw.north27.coachingapp.model.result.SignInResult
import tw.north27.coachingapp.model.result.VersionResult

interface IApiService {

    @GET
    suspend fun getVersionCtrlResult(): Response<VersionResult>

    @FormUrlEncoded
    @POST
    suspend fun checkSignIn(@Field("account") account: String, @Field("password") password: String? = null): Response<SignInResult>

}