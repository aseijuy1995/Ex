package edu.yujie.retrofitex

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    companion object {
        var retrofit: Retrofit? = null

        fun init(baseUrl: String, client: OkHttpClient, ) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        inline fun <reified T> create(): T = retrofit!!.create(T::class.java)

    }
}