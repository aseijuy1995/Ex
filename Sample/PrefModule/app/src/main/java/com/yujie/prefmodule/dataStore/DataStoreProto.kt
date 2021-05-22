package com.yujie.prefmodule.dataStore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

inline fun <T, R> DataStore<T>.getDelegate(crossinline transform: suspend (value: T) -> R): Flow<R> = data.map(transform)

fun <T> DataStore<T>.getFlow(): Flow<T> = data

suspend fun <T> DataStore<T>.get(): T = data.first()