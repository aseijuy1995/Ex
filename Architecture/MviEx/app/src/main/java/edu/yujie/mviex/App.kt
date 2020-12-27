package edu.yujie.mviex

import android.app.Application
import edu.yujie.mviex.util.OkHttpUtil
import edu.yujie.mviex.util.RetrofitManager
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
        single<ApiService> { ApiService(get()) }

        single<SearchRepo> { SearchRepo(get() as ApiService) }

        viewModel<SearchViewModel> { SearchViewModel(get() as SearchRepo) }
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