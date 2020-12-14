package edu.yujie.retrofitex

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpUtil private constructor(private val context: Context) {
    private val TAG = javaClass.simpleName
    val client: OkHttpClient

    companion object : SingletonProperty<OkHttpUtil, Context>(::OkHttpUtil)

    init {
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)//連接超時
            .readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
            .writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
            .callTimeout(60, TimeUnit.SECONDS)//完整請求超時
            .retryOnConnectionFailure(true)//失敗重連
//            .addNetworkInterceptor(CacheInterceptor)
            .build()
    }

    fun mapToBody(map: Map<String, String>): RequestBody {
        val builder = FormBody.Builder()
        map.forEach { builder.add(it.key, it.value) }
        return builder.build()
    }

    fun get(url: String, params: Map<String, String>?): Request {
        val builder = url.toHttpUrlOrNull()!!.newBuilder()
        params?.forEach { builder.addQueryParameter(it.key, it.value) }
        return Request.Builder().url(builder.build()).get().build()
    }

    fun head(url: String, params: Map<String, String>?): Request {
        val builder = url.toHttpUrlOrNull()!!.newBuilder()
        params?.forEach { builder.addQueryParameter(it.key, it.value) }
        return Request.Builder().url(builder.build()).head().build()
    }

    fun post(url: String, body: RequestBody) = Request.Builder().url(url).post(body).build()

    fun delete(url: String, body: RequestBody) = Request.Builder().url(url).delete(body).build()

    fun put(url: String, body: RequestBody) = Request.Builder().url(url).put(body).build()

    fun patch(url: String, body: RequestBody) = Request.Builder().url(url).patch(body).build()

    fun socket(url: String) = Request.Builder().url(url).build()

    fun sync(request: Request): String {
        return client.newCall(request).execute().use {
            if (!it.isSuccessful) throw IOException("$TAG Unexpected code $it")

            it.headers.forEach {
                println("$TAG name = ${it.first}, value = ${it.second}")
            }
            it.body!!.string()
        }
    }

    fun async(request: Request, callback: Callback) = client.newCall(request).enqueue(callback)

    //
    fun newWebSocket(request: Request, listener: WebSocketListener): WebSocket {
        val webSocket = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
        return webSocket
    }

}

fun OkHttpUtil.syncGet(url: String, params: Map<String, String>? = null) = sync(get(url, params))

fun OkHttpUtil.asyncGet(url: String, params: Map<String, String>? = null, callback: Callback) = async(get(url, params), callback)

fun OkHttpUtil.syncHead(url: String, params: Map<String, String>? = null) = sync(head(url, params))

fun OkHttpUtil.asyncHead(url: String, params: Map<String, String>? = null, callback: Callback) = async(head(url, params), callback)

fun OkHttpUtil.syncPost(url: String, params: Map<String, String>) = sync(post(url, mapToBody(params)))

fun OkHttpUtil.asyncPost(url: String, params: Map<String, String>, callback: Callback) = async(post(url, mapToBody(params)), callback)

fun OkHttpUtil.syncDelete(url: String, params: Map<String, String>) = sync(delete(url, mapToBody(params)))

fun OkHttpUtil.asyncDelete(url: String, params: Map<String, String>, callback: Callback) = async(delete(url, mapToBody(params)), callback)

fun OkHttpUtil.syncPut(url: String, params: Map<String, String>) = sync(put(url, mapToBody(params)))

fun OkHttpUtil.asyncPut(url: String, params: Map<String, String>, callback: Callback) = async(put(url, mapToBody(params)), callback)

fun OkHttpUtil.syncPatch(url: String, params: Map<String, String>) = sync(patch(url, mapToBody(params)))

fun OkHttpUtil.asyncPatch(url: String, params: Map<String, String>, callback: Callback) = async(patch(url, mapToBody(params)), callback)

fun OkHttpUtil.webSocket(url: String, listener: WebSocketListener) = newWebSocket(socket(url), listener)