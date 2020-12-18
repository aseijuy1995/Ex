package edu.yujie.retrofitex

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {

    companion object {
        var retrofit: Retrofit? = null

        fun init(context: Context) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://www.letsgoshopping.com.tw/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpUtil.get(context).client)
                .build()
        }

        inline fun <reified T> create(): T = retrofit!!.create(T::class.java)

    }
}