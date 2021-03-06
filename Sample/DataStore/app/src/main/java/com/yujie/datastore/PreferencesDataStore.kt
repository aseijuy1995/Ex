package com.yujie.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val PREF_DEFAULT_NAME = "PREF_DEFAULT_NAME"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_DEFAULT_NAME)

fun <T> DataStore<Preferences>.getDelegate(prefKey: Preferences.Key<T>, defaultValue: T): Flow<T> {
		return data.map {
				it[prefKey]
						?: defaultValue
		}
}

suspend fun <T> DataStore<Preferences>.setDelegate(prefKey: Preferences.Key<T>): Preferences {
		return edit {
				it[prefKey]
		}
}

fun DataStore<Preferences>.getInt(key: String, value: Int): Flow<Int> = getDelegate(intPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setInt(key: String): Preferences = setDelegate(intPreferencesKey(key))

fun DataStore<Preferences>.getDouble(key: String, value: Double): Flow<Double> = getDelegate(doublePreferencesKey(key), value)

suspend fun DataStore<Preferences>.setDouble(key: String): Preferences = setDelegate(doublePreferencesKey(key))

fun DataStore<Preferences>.getString(key: String, value: String): Flow<String> = getDelegate(stringPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setString(key: String): Preferences = setDelegate(stringPreferencesKey(key))

fun DataStore<Preferences>.getBoolean(key: String, value: Boolean): Flow<Boolean> = getDelegate(booleanPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setBoolean(key: String): Preferences = setDelegate(booleanPreferencesKey(key))

fun DataStore<Preferences>.getFloat(key: String, value: Float): Flow<Float> = getDelegate(floatPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setFloat(key: String): Preferences = setDelegate(floatPreferencesKey(key))

fun DataStore<Preferences>.getLong(key: String, value: Long): Flow<Long> = getDelegate(longPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setLong(key: String): Preferences = setDelegate(longPreferencesKey(key))

fun DataStore<Preferences>.getSetString(key: String, value: Set<String>): Flow<Set<String>> = getDelegate(stringSetPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setSetString(key: String): Preferences = setDelegate(stringSetPreferencesKey(key))