package tw.north27.coachingapp.const

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.repository.StartRepository
import tw.north27.coachingapp.util.http.OkHttpUtil
import tw.north27.coachingapp.util.http.RetrofitManager
import tw.north27.coachingapp.viewModel.StartViewModel

val utilModules = module {
    single { OkHttpUtil.get(androidContext()) }
    single<IApiService> {
        RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client)
        RetrofitManager.create<IApiService>()
    }
    viewModel { StartViewModel(StartRepository(get())) }
}

val modelModules = module {

}

val repoModules = module {

}

val viewModules = module {

}
