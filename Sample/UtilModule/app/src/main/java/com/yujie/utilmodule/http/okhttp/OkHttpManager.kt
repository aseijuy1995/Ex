package com.yujie.utilmodule.http.okhttp

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

object OkHttpManager {

		private var client: OkHttpClient? = null

		fun get(config: OkHttpConfig): Entity {
				client = OkHttpClient.Builder().configOkHttpClient(config = config)
				return Entity(client = client!!)
		}

		fun reGet(config: OkHttpConfig): Entity {
				val client = client
						?: throw RuntimeException("OkHttpClient is not initialized")
				val newClient = client.newBuilder().configOkHttpClient(config = config)
				return Entity(client = newClient)
		}

		private fun OkHttpClient.Builder.configOkHttpClient(config: OkHttpConfig): OkHttpClient {
				val client = connectTimeout(config.connectSec, TimeUnit.SECONDS)
						.readTimeout(config.readSec, TimeUnit.SECONDS)
						.writeTimeout(config.writeSec, TimeUnit.SECONDS)
						.callTimeout(config.callSec, TimeUnit.SECONDS)
						.retryOnConnectionFailure(config.isReConnect)
						.addInterceptor(config.logIntcp)
						.apply {
								if (config.authReqIntcp != null) addInterceptor(config.authReqIntcp)
								if (config.authRspIntcp != null) addInterceptor(config.authRspIntcp)
						}
						.build()
				return client
		}

		class Entity(val client: OkHttpClient) {
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
}