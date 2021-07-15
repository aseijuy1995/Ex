package com.yujie.core_lib.pref

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.yujie.core_lib.UserPref
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
            throw CorruptionException("Cannot read UserPref.proto.", e)
        }
    }

    override suspend fun writeTo(t: UserPref, output: OutputStream) {
        return t.writeTo(output)
    }
}

fun DataStore<UserPref>.getId(): Flow<String> = getDelegate(UserPref::getId)

suspend fun DataStore<UserPref>.setId(id: String): UserPref = setUserPref(id = id)

fun DataStore<UserPref>.getAccount(): Flow<String> = getDelegate(UserPref::getAccount)

suspend fun DataStore<UserPref>.setAccount(account: String): UserPref = setUserPref(account = account)

fun DataStore<UserPref>.getPassword(): Flow<String> = getDelegate(UserPref::getPassword)

suspend fun DataStore<UserPref>.setPassword(password: String): UserPref = setUserPref(password = password)

fun DataStore<UserPref>.getTokenType(): Flow<UserPref.TokenType> = getDelegate(UserPref::getTokenType)

suspend fun DataStore<UserPref>.setTokenType(tokenType: UserPref.TokenType): UserPref = setUserPref(tokenType = tokenType)

fun DataStore<UserPref>.getAccessToken(): Flow<String> = getDelegate(UserPref::getAccessToken)

suspend fun DataStore<UserPref>.setAccessToken(accessToken: String): UserPref = setUserPref(accessToken = accessToken)

fun DataStore<UserPref>.getRefreshToken(): Flow<String> = getDelegate(UserPref::getRefreshToken)

suspend fun DataStore<UserPref>.setRefreshToken(refreshToken: String): UserPref = setUserPref(refreshToken = refreshToken)

fun DataStore<UserPref>.getExpiresTime(): Flow<Long> = getDelegate(UserPref::getExpiresTime)

suspend fun DataStore<UserPref>.setExpiresTime(expireTime: Long): UserPref = setUserPref(expiresTime = expireTime)

fun DataStore<UserPref>.getIsFirst(): Flow<Boolean> = getDelegate(UserPref::getIsFirst)

suspend fun DataStore<UserPref>.setIsFirst(isFirst: Boolean): UserPref = setUserPref(isFirst = isFirst)

fun DataStore<UserPref>.getPushToken(): Flow<String> = getDelegate(UserPref::getPushToken)

suspend fun DataStore<UserPref>.setPushToken(fcmToken: String): UserPref = setUserPref(pushToken = fcmToken)

fun DataStore<UserPref>.getAuth(): Flow<UserPref.Authority> = getDelegate(UserPref::getAuth)

suspend fun DataStore<UserPref>.setAuth(auth: UserPref.Authority): UserPref = setUserPref(auth = auth)

suspend fun DataStore<UserPref>.setUserPref(
    id: String? = null,
    account: String? = null,
    password: String? = null,
    tokenType: UserPref.TokenType? = null,
    accessToken: String? = null,
    refreshToken: String? = null,
    expiresTime: Long? = null,
    isFirst: Boolean? = null,
    pushToken: String? = null,
    auth: UserPref.Authority? = null,
): UserPref {
    return updateData {
        it.toBuilder().apply {
            id?.let { setId(it) }
            account?.let { setAccount(it) }
            password?.let { setPassword(it) }
            tokenType?.let { setTokenType(it) }
            accessToken?.let { setAccessToken(it) }
            refreshToken?.let { setRefreshToken(it) }
            expiresTime?.let { setExpiresTime(it) }
            isFirst?.let { setIsFirst(it) }
            pushToken?.let { setPushToken(it) }
            auth?.let { setAuth(it) }
        }.build()
    }
}

suspend fun DataStore<UserPref>.clear(): UserPref {
    return updateData {
        it.toBuilder()
            .setId("")
            .setAccount("")
            .setPassword("")
            .setTokenType(UserPref.TokenType.Unknown)
            .setAccessToken("")
            .setRefreshToken("")
            .setExpiresTime(0L)
            .setIsFirst(false)
            .setPushToken("")
            .setAuth(UserPref.Authority.NONE)
            .build()
    }
}

