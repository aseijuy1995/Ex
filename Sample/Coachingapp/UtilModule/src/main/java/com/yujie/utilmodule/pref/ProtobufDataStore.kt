package com.yujie.utilmodule.pref

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal inline fun <T, R> DataStore<T>.getDelegate(crossinline transform: suspend (value: T) -> R): Flow<R> = data.map(transform)