package tw.north27.coachingapp.module.http

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    var retrofit: Retrofit? = null

    fun get(
        baseUrl: String,
        client: OkHttpClient,
        factory: Converter.Factory = GsonConverterFactory.create()
    ): RetrofitManager {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(factory)
            .client(client)
            .build()
        return this
    }

    inline fun <reified T> create(): T = retrofit!!.create(T::class.java)

    fun changeBaseUrl(baseUrl: String): RetrofitManager {
        retrofit = retrofit?.newBuilder()?.baseUrl(baseUrl)?.build()
        return this
    }

}