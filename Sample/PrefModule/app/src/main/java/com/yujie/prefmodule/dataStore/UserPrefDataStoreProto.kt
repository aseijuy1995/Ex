package com.yujie.prefmodule.dataStore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.yujie.prefModule.protobuf.UserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

/**
 * Protobuf DataStore
 */

val USER_PREF_NAME = "user_pref.pb"

val Context.dataStoreUserPref: DataStore<UserPref> by dataStore(
		USER_PREF_NAME,
		UserPrefSerializer
)

object UserPrefSerializer : Serializer<UserPref> {
		override val defaultValue: UserPref
				get() = UserPref.getDefaultInstance()

		override suspend fun readFrom(input: InputStream): UserPref {
				try {
						return UserPref.parseFrom(input)
				} catch (e: InvalidProtocolBufferException) {
						throw CorruptionException("Cannot read proto.", e)
				}
		}

		override suspend fun writeTo(userPref: UserPref, output: OutputStream) {
				userPref.writeTo(output)
		}
}

fun DataStore<UserPref>.getUuid(): Flow<Long> = getDelegate(UserPref::getUuid)

fun DataStore<UserPref>.getAccount(): Flow<String> = getDelegate(UserPref::getAccount)

fun DataStore<UserPref>.getAuth(): Flow<String> = getDelegate(UserPref::getAuth)

fun DataStore<UserPref>.getAccessToken(): Flow<String> = getDelegate(UserPref::getAccessToken)

fun DataStore<UserPref>.getRefreshToken(): Flow<String> = getDelegate(UserPref::getRefreshToken)

fun DataStore<UserPref>.getDeviceId(): Flow<String> = getDelegate(UserPref::getDeviceId)

fun DataStore<UserPref>.getFcmToken(): Flow<String> = getDelegate(UserPref::getFcmToken)

fun DataStore<UserPref>.getIsFirst(): Flow<Boolean> = getDelegate(UserPref::getIsFirst)

private inline fun <T, R> DataStore<T>.getDelegate(crossinline transform: suspend (value: T) -> R) = data.map(transform)

suspend fun DataStore<UserPref>.setDelegate(
		uuid: Long? = null,
		account: String? = null,
		auth: String? = null,
		accessToken: String? = null,
		refreshToken: String? = null,
		deviceId: String? = null,
		fcmToken: String? = null,
		isFirst: Boolean? = null
): DataStore<UserPref> {
		updateData {
				it.toBuilder().apply {
						uuid?.let { setUuid(it) }
						account?.let { setAccount(it) }
						auth?.let { setAuth(it) }
						accessToken?.let { setAccessToken(it) }
						refreshToken?.let { setRefreshToken(it) }
						deviceId?.let { setDeviceId(it) }
						fcmToken?.let { setFcmToken(it) }
						isFirst?.let { setIsFirst(it) }
				}.build()
		}
		return this
}

suspend fun DataStore<UserPref>.setUuid(uuid: Long): DataStore<UserPref> {
		setDelegate(uuid = uuid)
		return this
}

suspend fun DataStore<UserPref>.setAccount(account: String): DataStore<UserPref> {
		setDelegate(account = account)
		return this
}

suspend fun DataStore<UserPref>.setAuth(auth: String): DataStore<UserPref> {
		setDelegate(auth = auth)
		return this
}

suspend fun DataStore<UserPref>.setAccessToken(accessToken: String): DataStore<UserPref> {
		setDelegate(accessToken = accessToken)
		return this
}

suspend fun DataStore<UserPref>.setRefreshToken(refreshToken: String): DataStore<UserPref> {
		setDelegate(refreshToken = refreshToken)
		return this
}

suspend fun DataStore<UserPref>.setDeviceId(deviceId: String): DataStore<UserPref> {
		setDelegate(deviceId = deviceId)
		return this
}

suspend fun DataStore<UserPref>.setFcmToken(fcmToken: String): DataStore<UserPref> {
		setDelegate(fcmToken = fcmToken)
		return this
}

suspend fun DataStore<UserPref>.setIsFirst(isFirst: Boolean): DataStore<UserPref> {
		setDelegate(isFirst = isFirst)
		return this
}

suspend fun DataStore<UserPref>.clear(): DataStore<UserPref> {
		updateData {
				it.toBuilder()
						.setUuid(0L)
						.setAccount("")
						.setAuth("")
						.setAccessToken("")
						.setRefreshToken("")
						.setDeviceId("")
						.setFcmToken("")
						.setIsFirst(false)
						.build()
		}
		return this
}


