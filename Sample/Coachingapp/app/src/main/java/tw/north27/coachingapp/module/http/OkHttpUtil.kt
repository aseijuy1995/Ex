package tw.north27.coachingapp.module.http

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

var client: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)//連接超時
    .readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
    .writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
    .callTimeout(60, TimeUnit.SECONDS)//完整請求超時
    .retryOnConnectionFailure(true)//失敗重連
    .addInterceptor(HttpLoggingInterceptor(BaseHttpLoggingInterceptor).apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .pingInterval(40, TimeUnit.SECONDS)
    .build()


class OkHttpUtil(
    private val context: Context,
    private val tokenInterceptor: Interceptor? = null,
    private val tokenExpiredInterceptor: Interceptor? = null
) {

    private val TAG = javaClass.simpleName

    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)//連接超時
        .readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
        .writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
        .callTimeout(60, TimeUnit.SECONDS)//完整請求超時
        .retryOnConnectionFailure(true)//失敗重連
        .apply {
            tokenInterceptor?.let { addInterceptor(it) }//Auth
            tokenExpiredInterceptor?.let { addInterceptor(it) }//Auth Expired
        }
        .addInterceptor(HttpLoggingInterceptor(BaseHttpLoggingInterceptor).apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .pingInterval(40, TimeUnit.SECONDS)
        .build()

    fun get(url: String, params: Map<String, String>): Request {
        val builder = url.toHttpUrl().newBuilder()
        params.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.addEncodedQueryParameter(it.key, it.value)
        }
        return Request.Builder().url(builder.build()).get().build()
    }

    fun head(url: String, params: Map<String, String>): Request {
        val builder = url.toHttpUrl().newBuilder()
        params.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.addEncodedQueryParameter(it.key, it.value)
        }
        return Request.Builder().url(builder.build()).head().build()
    }

    fun post(url: String, body: RequestBody) = Request.Builder().url(url).post(body).build()

    fun delete(url: String, body: RequestBody) = Request.Builder().url(url).delete(body).build()

    fun put(url: String, body: RequestBody) = Request.Builder().url(url).put(body).build()

    fun patch(url: String, body: RequestBody) = Request.Builder().url(url).patch(body).build()

    fun request(url: String) = Request.Builder().url(url).build()

    //--------------------------------------------------------------------------------

    //json
    fun jsonToBody(json: String): RequestBody {
        val mediaType = "application/json;charset=utf-8".toMediaType()
        return json.toRequestBody(mediaType)
    }

    //from-data
    fun fromDataToBody(map: Map<String, String>?): RequestBody {
        val builder = FormBody.Builder()
        map?.forEach {
            if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
                builder.add(it.key, it.value)
        }
        return builder.build()
    }

    //--------------------------------------------------------------------------------

    fun sync(request: Request): Response = client.newCall(request).execute().run {
        if (!isSuccessful) throw  IOException("$TAG Unexpected code ${this.code}")
        this
    }

    fun async(request: Request, callback: Callback) = client.newCall(request).enqueue(callback)

    fun webSocket(request: Request, listener: WebSocketListener) = client.newWebSocket(request, listener)

}