package com.yujie.utilmodule.http

import com.yujie.utilmodule.http.HttpAuth
import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class OkHttpUtil(
		private val cxt: Context,
		private val httpAuth: HttpAuth = HttpAuth.NONE,
		private val builder: ((OkHttpClient.Builder) -> OkHttpClient.Builder)? = null
) {
		private val clientBuilder = OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)//連接超時
				.readTimeout(30, TimeUnit.SECONDS)//IO讀取超時
				.writeTimeout(10, TimeUnit.SECONDS)//IO寫入超時
				.callTimeout(60, TimeUnit.SECONDS)//完整請求超時
				.retryOnConnectionFailure(true)//失敗重連
				.addInterceptor(BaseAuthTokenRequestInterceptor(cxt, httpAuth))
				.addInterceptor(BaseAuthTokenResponseInterceptor(cxt, httpAuth))
				.addInterceptor(httpLoggingInterceptor)
				.pingInterval(40, TimeUnit.SECONDS)

		val client: OkHttpClient = builder?.invoke(clientBuilder)?.build()
				?: clientBuilder.build()

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

		/**
		 * Json to Body
		 * */
		fun jsonToBody(json: String): RequestBody {
				val mediaType = "application/json;charset=utf-8".toMediaType()
				return json.toRequestBody(mediaType)
		}

		/**
		 * From Data to Body
		 * */
		fun fromDataToBody(map: Map<String, String>?): RequestBody {
				val builder = FormBody.Builder()
				map?.forEach {
						if (it.key.trim().isNotEmpty() && it.value.trim().isNotEmpty())
								builder.add(it.key, it.value)
				}
				return builder.build()
		}

		/**
		 * 同步
		 * */
		fun sync(request: Request): Response = client.newCall(request).execute().run {
				if (!isSuccessful) throw  IOException("Unexpected code ${this.code}")
				this
		}

		/**
		 * 異步
		 * */
		fun async(request: Request, callback: Callback) = client.newCall(request).enqueue(callback)

		/**
		 * WebSocket
		 * */
		fun webSocket(request: Request, listener: WebSocketListener) = client.newWebSocket(request, listener)
}