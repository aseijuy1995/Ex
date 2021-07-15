package com.yujie.core_lib.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ResponseResults<out T> {

		companion object {
				fun <T> informational(code: Int, header: Headers): ResponseResults<T> = Informational(code, header)

				fun <T> successful(code: Int, header: Headers, data: T): ResponseResults<T> = Successful<T>(code, header, data)

				fun <T> redirection(code: Int, header: Headers): ResponseResults<T> = Redirection(code, header)

				fun <T> clientErrors(code: Int, headers: Headers, e: HttpException): ResponseResults<T> = ClientErrors(code, headers, e)

				fun <T> serverError(code: Int, headers: Headers, e: HttpException): ResponseResults<T> = ServerError(code, headers, e)

				fun <T> netWorkError(e: IOException): ResponseResults<T> = NetWorkError(e)
		}

		//1XX
		data class Informational<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

		//2xx
		data class Successful<out T>(val code: Int, val headers: Headers, val data: T) : ResponseResults<T>()

		//3xx
		data class Redirection<out T>(val code: Int, val header: Headers) : ResponseResults<T>()

		//4xx
		data class ClientErrors(val code: Int, val headers: Headers, val e: HttpException) : ResponseResults<Nothing>()

		//5xx
		data class ServerError(val code: Int, val headers: Headers, val e: HttpException) : ResponseResults<Nothing>()

		//IOException
		data class NetWorkError(val e: IOException) : ResponseResults<Nothing>()
}

suspend fun <T> safeApiResponseResults(data: suspend () -> Response<T>): ResponseResults<T> {
		return withContext(Dispatchers.IO) {
				try {
						val response = data.invoke()
						val code = response.code()
						val headers = response.headers()
						val body = response.body()
						when (code) {
								in 100 until 200 -> ResponseResults.informational(code, headers)
								in 200 until 300 -> ResponseResults.successful(code, headers, body!!)
								in 300 until 400 -> ResponseResults.redirection(code, headers)
								in 400 until 500 -> ResponseResults.clientErrors(code, headers, HttpException(Response.error<String>(code, "$code client errors".toResponseBody())))
								else -> ResponseResults.serverError(code, headers, HttpException(Response.error<String>(code, "$code server errors".toResponseBody())))
						}
				} catch (e: IOException) {
						ResponseResults.netWorkError(e)
				} catch (e: Exception) {
						e.printStackTrace()
						ResponseResults.netWorkError(e as IOException)
				}
		}
}
