package tw.north27.coachingapp.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Preferences DataStore
 * */

const val PREF_DEFAULT_NAME = "PREF_DEFAULT_NAME"

fun Context.createDataStorePref(name: String = PREF_DEFAULT_NAME): DataStore<Preferences> = createDataStore(name)

private fun <T> DataStore<Preferences>.setDelegate(prefKey: Preferences.Key<T>, value: T, scope: CoroutineScope): Job {
    return scope.launch {
        edit { it[prefKey] = value }
    }
}

private fun <T> DataStore<Preferences>.getDelegate(prefKey: Preferences.Key<T>, default: T): Flow<T> {
    return data.map { it[prefKey] ?: default }
}

fun DataStore<Preferences>.setInt(key: String, value: Int = 0, scope: CoroutineScope) = setDelegate(intPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getInt(key: String, default: Int = 0): Flow<Int> = getDelegate(intPreferencesKey(key), default)

fun DataStore<Preferences>.setDouble(key: String, value: Double = 0.0, scope: CoroutineScope) = setDelegate(doublePreferencesKey(key), value, scope)

fun DataStore<Preferences>.getDouble(key: String, default: Double = 0.0): Flow<Double> = getDelegate(doublePreferencesKey(key), default)

fun DataStore<Preferences>.setString(key: String, value: String = "", scope: CoroutineScope) = setDelegate(stringPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getString(key: String, default: String = ""): Flow<String> = getDelegate(stringPreferencesKey(key), default)

fun DataStore<Preferences>.setBoolean(key: String, value: Boolean = false, scope: CoroutineScope) = setDelegate(booleanPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getBoolean(key: String, default: Boolean = false): Flow<Boolean> = getDelegate(booleanPreferencesKey(key), default)

fun DataStore<Preferences>.setFloat(key: String, value: Float = 0F, scope: CoroutineScope) = setDelegate(floatPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getFloat(key: String, default: Float = 0F): Flow<Float> = getDelegate(floatPreferencesKey(key), default)

fun DataStore<Preferences>.setLong(key: String, value: Long = 0, scope: CoroutineScope) = setDelegate(longPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getLong(key: String, default: Long = 0): Flow<Long> = getDelegate(longPreferencesKey(key), default)

fun DataStore<Preferences>.setStringSet(key: String, value: Set<String> = emptySet(), scope: CoroutineScope) = setDelegate(stringSetPreferencesKey(key), value, scope)

fun DataStore<Preferences>.getStringSet(key: String, default: Set<String> = emptySet()): Flow<Set<String>> = getDelegate(stringSetPreferencesKey(key), default)

/**
 * Proto DataStore
 * */

fun <T> Context.createDataStoreProto(fileName: String, serializer: Serializer<T>): DataStore<T> = createDataStore(fileName, serializer)

inline fun <T> DataStore<T>.setValue(scope: CoroutineScope, crossinline transform: suspend (t: T) -> T): Job {
    return scope.launch {
        updateData {
            transform(it)
        }
    }
}

inline fun <T, S> DataStore<T>.getValue(
    crossinline transform: (suspend (t: T) -> S)
): Flow<S> {
    return data.map(transform)
}
