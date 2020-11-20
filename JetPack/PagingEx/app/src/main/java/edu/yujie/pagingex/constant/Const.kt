package edu.yujie.pagingex.constant

import edu.yujie.pagingex.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


fun getConcertList(position: Int, count: Int): List<Concert> {
    val list = mutableListOf<Concert>()
    for (i in position..count) {
        val concert = Concert(id = i, name = "Name = $i")
        list.add(concert)
    }
    return list
}

val appModule = module {
    single { AppDatabase.get(androidContext()) }
    single { PagingRepository() }
    viewModel { PagingViewModel(get()) }
}