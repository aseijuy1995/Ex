package com.yujie.core_lib.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val PREF_DEFAULT_NAME = "PREF_DEFAULT_NAME"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
		name = PREF_DEFAULT_NAME,
		/**
		 * SharedPreferences遷移至DataStore
		 * */
//		produceMigrations = { context -> listOf(SharedPreferencesMigration(context, PREF_DEFAULT_NAME)) }
)

fun <T> DataStore<Preferences>.getDelegate(prefKey: Preferences.Key<T>, defaultValue: T): Flow<T> {
		return data.map {
				it[prefKey]
						?: defaultValue
		}
}

suspend fun <T> DataStore<Preferences>.setDelegate(prefKey: Preferences.Key<T>, value: T): Preferences {
		return edit {
				it[prefKey] = value
		}
}

fun DataStore<Preferences>.getInt(key: String, value: Int): Flow<Int> = getDelegate(intPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setInt(key: String, value: Int): Preferences = setDelegate(intPreferencesKey(key), value)

fun DataStore<Preferences>.getDouble(key: String, value: Double): Flow<Double> = getDelegate(doublePreferencesKey(key), value)

suspend fun DataStore<Preferences>.setDouble(key: String, value: Double): Preferences = setDelegate(doublePreferencesKey(key), value)

fun DataStore<Preferences>.getString(key: String, value: String): Flow<String> = getDelegate(stringPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setString(key: String, value: String): Preferences = setDelegate(stringPreferencesKey(key), value)

fun DataStore<Preferences>.getBoolean(key: String, value: Boolean): Flow<Boolean> = getDelegate(booleanPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setBoolean(key: String, value: Boolean): Preferences = setDelegate(booleanPreferencesKey(key), value)

fun DataStore<Preferences>.getFloat(key: String, value: Float): Flow<Float> = getDelegate(floatPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setFloat(key: String, value: Float): Preferences = setDelegate(floatPreferencesKey(key), value)

fun DataStore<Preferences>.getLong(key: String, value: Long): Flow<Long> = getDelegate(longPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setLong(key: String, value: Long): Preferences = setDelegate(longPreferencesKey(key), value)

fun DataStore<Preferences>.getSetString(key: String, value: Set<String>): Flow<Set<String>> = getDelegate(stringSetPreferencesKey(key), value)

suspend fun DataStore<Preferences>.setSetString(key: String, value: Set<String>): Preferences = setDelegate(stringSetPreferencesKey(key), value)