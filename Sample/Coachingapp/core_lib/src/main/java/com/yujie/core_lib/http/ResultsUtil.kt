package com.yujie.core_lib.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class Results<out T> {

		companion object {
				fun <T> successful(data: T): Results<T> = Successful<T>(data)

				fun <T> clientErrors(e: HttpException): Results<T> = ClientErrors(e)

				fun <T> netWorkError(e: IOException): Results<T> = NetWorkError(e)
		}

		//Success
		data class Successful<out T>(val data: T) : Results<T>()

		//HttpException
		data class ClientErrors(val e: HttpException) : Results<Nothing>()

		//IOException
		data class NetWorkError(val e: IOException) : Results<Nothing>()
}

suspend fun <T> safeApiResults(data: suspend () -> T): Results<T> {
		return withContext(Dispatchers.IO) {
				try {
						Results.successful(data.invoke())
				} catch (e: HttpException) {
						Results.clientErrors(e)
				} catch (e: IOException) {
						Results.netWorkError(e)
				}
		}
}
