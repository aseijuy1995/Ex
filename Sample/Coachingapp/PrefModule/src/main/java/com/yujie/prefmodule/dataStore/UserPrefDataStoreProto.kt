package com.yujie.prefmodule.dataStore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.yujie.prefmodule.protobuf.UserPref
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import java.io.OutputStream

/**
 * Protobuf DataStore
 */

val USER_PREF_NAME = "user_pref.pb"

val Context.userPref: DataStore<UserPref> by dataStore(USER_PREF_NAME, UserPrefSerializer)

object UserPrefSerializer : Serializer<UserPref> {
		override val defaultValue: UserPref = UserPref.getDefaultInstance()

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

suspend fun DataStore<UserPref>.setUserPref(
		uuid: String? = null,
		account: String? = null,
		auth: UserPref.Authority? = null,
		accessToken: String? = null,
		refreshToken: String? = null,
		fcmToken: String? = null,
		isFirst: Boolean? = null
): UserPref {
		return updateData {
				it.toBuilder().apply {
						uuid?.let { setUuid(it) }
						account?.let { setAccount(it) }
						auth?.let { setAuth(it) }
						accessToken?.let { setAccessToken(it) }
						refreshToken?.let { setRefreshToken(it) }
						fcmToken?.let { setFcmToken(it) }
						isFirst?.let { setIsFirst(it) }
				}.build()
		}
}

fun DataStore<UserPref>.getUuid(): Flow<String> = getDelegate(UserPref::getUuid)

suspend fun DataStore<UserPref>.setUuid(uuid: String): UserPref = setUserPref(uuid = uuid)

fun DataStore<UserPref>.getAccount(): Flow<String> = getDelegate(UserPref::getAccount)

suspend fun DataStore<UserPref>.setAccount(account: String): UserPref = setUserPref(account = account)

fun DataStore<UserPref>.getAuth(): Flow<UserPref.Authority> = getDelegate(UserPref::getAuth)

suspend fun DataStore<UserPref>.setAuth(auth: UserPref.Authority): UserPref = setUserPref(auth = auth)

fun DataStore<UserPref>.getAccessToken(): Flow<String> = getDelegate(UserPref::getAccessToken)

suspend fun DataStore<UserPref>.setAccessToken(accessToken: String): UserPref = setUserPref(accessToken = accessToken)

fun DataStore<UserPref>.getRefreshToken(): Flow<String> = getDelegate(UserPref::getRefreshToken)

suspend fun DataStore<UserPref>.setRefreshToken(refreshToken: String): UserPref = setUserPref(refreshToken = refreshToken)

fun DataStore<UserPref>.getFcmToken(): Flow<String> = getDelegate(UserPref::getFcmToken)

suspend fun DataStore<UserPref>.setFcmToken(fcmToken: String): UserPref = setUserPref(fcmToken = fcmToken)

fun DataStore<UserPref>.getIsFirst(): Flow<Boolean> = getDelegate(UserPref::getIsFirst)

suspend fun DataStore<UserPref>.setIsFirst(isFirst: Boolean): UserPref = setUserPref(isFirst = isFirst)

suspend fun DataStore<UserPref>.clear(): UserPref {
		return updateData {
				it.toBuilder()
						.setUuid("")
						.setAccount("")
						.setAuth(UserPref.Authority.UNKNOWN)
						.setAccessToken("")
						.setRefreshToken("")
						.setFcmToken("")
						.setIsFirst(false)
						.build()
		}
}


