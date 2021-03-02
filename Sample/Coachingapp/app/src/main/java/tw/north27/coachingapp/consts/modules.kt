package tw.north27.coachingapp.consts

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.module.http.OkHttpUtil
import tw.north27.coachingapp.module.http.RetrofitManager
import tw.north27.coachingapp.repository.NotifyRepository
import tw.north27.coachingapp.repository.PublicRepository
import tw.north27.coachingapp.repository.UserRepository
import tw.north27.coachingapp.repository.inter.INotifyRepository
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.viewModel.NotifyViewModel
import tw.north27.coachingapp.viewModel.SignInViewModel
import tw.north27.coachingapp.viewModel.StartViewModel

val utilModules = module {
    single { OkHttpUtil(androidContext()) }
    single<RetrofitManager> { RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client) }

}

val modelModules = module {
//    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
    single<IApiService> { ApiService() }
}

val repoModules = module {
    single<IPublicRepository> { PublicRepository(get()) }
    single<IUserRepository> { UserRepository(get(), androidContext()) }
    single<INotifyRepository> { NotifyRepository(get()) }
}

val viewModules = module {
    viewModel { StartViewModel(androidApplication(), get(), get()) }
    viewModel { SignInViewModel(androidApplication(), get()) }
    viewModel { NotifyViewModel(get()) }
}
