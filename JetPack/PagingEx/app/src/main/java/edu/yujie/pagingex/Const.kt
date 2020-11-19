package edu.yujie.pagingex

//import edu.yujie.pagingex.paging2.AppDatabase
import ConcertDao
import edu.yujie.pagingex.paging2.AppDatabase
//import edu.yujie.pagingex.paging2.AppDatabase
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
    single<AppDatabase> { AppDatabase.get(androidContext()) }
    single<ConcertDao> { (get() as AppDatabase).concertDao() }
    single { PagingRepository(get()) }
    viewModel { PagingViewModel(get()) }
}