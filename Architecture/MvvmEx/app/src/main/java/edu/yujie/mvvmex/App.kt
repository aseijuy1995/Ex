package edu.yujie.mvvmex

import android.app.Application
import edu.yujie.mvvmex.util.OkHttpUtil
import edu.yujie.mvvmex.util.RetrofitManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

//https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}
const val baseUrl = "https://api.github.com"

class App : Application() {
    private val module = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
        single<RetrofitManager> { RetrofitManager.get(baseUrl, (get() as OkHttpUtil).client) }
        single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
        single<SearchRepo> { SearchRepo(get()) }
        viewModel<SearchViewModel> { SearchViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(module)
        }
    }
}