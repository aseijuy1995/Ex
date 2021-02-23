package tw.north27.coachingapp.const

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.repository.SignInRepository
import tw.north27.coachingapp.repository.StartRepository
import tw.north27.coachingapp.repository.inter.ISignInRepository
import tw.north27.coachingapp.repository.inter.IStartRepository
import tw.north27.coachingapp.util.http.OkHttpUtil
import tw.north27.coachingapp.util.http.RetrofitManager
import tw.north27.coachingapp.viewModel.SignInViewModel
import tw.north27.coachingapp.viewModel.StartViewModel

val utilModules = module {
    single { OkHttpUtil.get(androidContext()) }
    single<RetrofitManager> { RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client) }

}

val modelModules = module {
//    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
    single<IApiService> { ApiService() }
}

val repoModules = module {
    single<IStartRepository> { StartRepository(get()) }
    single<ISignInRepository> { SignInRepository(get()) }
}

val viewModules = module {
    viewModel { StartViewModel(androidApplication(), get()) }
    viewModel { SignInViewModel(androidApplication(), get()) }
}
