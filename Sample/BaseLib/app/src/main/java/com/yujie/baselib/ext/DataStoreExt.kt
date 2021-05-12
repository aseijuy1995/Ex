package com.yujie.baselib.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Preferences DataStore
 * */

const val PREF_DEFAULT_NAME = "PREF_DEFAULT_NAME"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_DEFAULT_NAME)

private fun <T> DataStore<Preferences>.getDelegate(prefKey: Preferences.Key<T>, defaultValue: T): Flow<T> {
		return data.map {
				it[prefKey]
						?: defaultValue
		}
}

private suspend fun <T> DataStore<Preferences>.setDelegate(preKey: Preferences.Key<T>, value: T) {
		edit {
				it[preKey] = value
		}
}

fun DataStore<Preferences>.getInt(key: String, value: Int = 0): Flow<Int> = getDelegate(intPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setInt(key: String, value: Int) = setDelegate(intPreferencesKey(key), value)

fun DataStore<Preferences>.getFloat(key: String, value: Float = 0F): Flow<Float> = getDelegate(floatPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setFloat(key: String, value: Float) = setDelegate(floatPreferencesKey(key), value)

fun DataStore<Preferences>.getDouble(key: String, value: Double = 0.0): Flow<Double> = getDelegate(doublePreferencesKey(key), value)

suspend fun DataStore<Preferences>.setDouble(key: String, value: Double) = setDelegate(doublePreferencesKey(key), value)

fun DataStore<Preferences>.getLong(key: String, value: Long = 0L): Flow<Long> = getDelegate(longPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setLong(key: String, value: Long) = setDelegate(longPreferencesKey(key), value)

fun DataStore<Preferences>.getBoolean(key: String, value: Boolean = false): Flow<Boolean> = getDelegate(booleanPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setBoolean(key: String, value: Boolean) = setDelegate(booleanPreferencesKey(key), value)

fun DataStore<Preferences>.getString(key: String, value: String = ""): Flow<String> = getDelegate(stringPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setString(key: String, value: String) = setDelegate(stringPreferencesKey(key), value)

fun DataStore<Preferences>.getStringSet(key: String, value: Set<String> = emptySet()): Flow<Set<String>> = getDelegate(stringSetPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setStringSet(key: String, value: Set<String>) = setDelegate(stringSetPreferencesKey(key), value)

/**
 * Protobuf DataStore
 */
fun main(args: Array<String>) {

}
//val Context.dataStoreProto: DataStore<T> by dataStore<T>(filename, serializer)

//object SettingsSerializer :Serializer<UserPreferences>

//fun main(args: Array<String>) {
//		println("123")
//}