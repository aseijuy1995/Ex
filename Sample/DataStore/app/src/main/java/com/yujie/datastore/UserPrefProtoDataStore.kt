package com.yujie.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.yujie.dataStore.UserPref
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import java.io.OutputStream

val PREF_USER_DEFAULT_NAME = "userPref.pb"

val Context.userPref: DataStore<UserPref> by dataStore(
		fileName = PREF_USER_DEFAULT_NAME,
		serializer = UserPrefSerializer
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

		override suspend fun writeTo(t: UserPref, output: OutputStream) {
				return t.writeTo(output)
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

fun DataStore<UserPref>.getPushToken(): Flow<String> = getDelegate(UserPref::getPushToken)

suspend fun DataStore<UserPref>.setPushToken(fcmToken: String): UserPref = setUserPref(pushToken = fcmToken)

fun DataStore<UserPref>.getIsFirst(): Flow<Boolean> = getDelegate(UserPref::getIsFirst)

suspend fun DataStore<UserPref>.setIsFirst(isFirst: Boolean): UserPref = setUserPref(isFirst = isFirst)

suspend fun DataStore<UserPref>.setUserPref(
		uuid: String? = null,
		account: String? = null,
		auth: UserPref.Authority? = null,
		accessToken: String? = null,
		refreshToken: String? = null,
		pushToken: String? = null,
		isFirst: Boolean? = null
): UserPref {
		return updateData {
				it.toBuilder().apply {
						uuid?.let { setUuid(it) }
						account?.let { setAccount(it) }
						auth?.let { setAuth(it) }
						accessToken?.let { setAccessToken(it) }
						refreshToken?.let { setRefreshToken(it) }
						pushToken?.let { setPushToken(it) }
						isFirst?.let { setIsFirst(it) }
				}.build()
		}
}

suspend fun DataStore<UserPref>.clear(): UserPref {
		return updateData {
				it.toBuilder()
						.setUuid("")
						.setAccount("")
						.setAuth(UserPref.Authority.UNKNOWN)
						.setAccessToken("")
						.setRefreshToken("")
						.setPushToken("")
						.setIsFirst(false)
						.build()
		}
}

