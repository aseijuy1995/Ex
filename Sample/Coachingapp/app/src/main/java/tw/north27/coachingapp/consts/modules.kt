package tw.north27.coachingapp.consts

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.chat.*
import tw.north27.coachingapp.media.*
import tw.north27.coachingapp.module.http.OkHttpUtil
import tw.north27.coachingapp.module.http.RetrofitManager
import tw.north27.coachingapp.notify.INotifyRepository
import tw.north27.coachingapp.notify.NotifyRepository
import tw.north27.coachingapp.notify.NotifyViewModel
import tw.north27.coachingapp.repository.PublicRepository
import tw.north27.coachingapp.repository.UserRepository
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.viewModel.SignInViewModel
import tw.north27.coachingapp.viewModel.StartViewModel

val utilModules = module {
    single<OkHttpUtil> { OkHttpUtil(androidContext()) }
    single<RetrofitManager> { RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client) }
    single<IChatModule> { ChatModule(get()) }
    single<IMediaModule>(named("image")) { MediaImageModule(androidContext()) }
    single<IMediaModule>(named("video")) { MediaVideoModule(androidContext()) }
    single<IMediaModule>(named("audio")) { MediaAudioModule(androidContext()) }
    single<IMediaCodecModule> { AudioMediaCodecModule(androidContext()) }

}

val modelModules = module {
//    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
    single<IApiService> { ApiService() }
}

val repoModules = module {
    single<IPublicRepository> { PublicRepository(get()) }
    single<IUserRepository> { UserRepository(get(), androidContext()) }
    single<INotifyRepository> { NotifyRepository(get()) }
    single<IChatRepository> { ChatRepository(get(), get()) }
    single<IMediaRepository> {
        MediaRepository(
            get<IMediaModule>(named("image")),
            get<IMediaModule>(named("video")),
            get<IMediaModule>(named("audio")),
            get<IMediaCodecModule>()
        )
    }

}

val viewModelModules = module {
    viewModel { StartViewModel(androidApplication(), get(), get()) }
    viewModel { SignInViewModel(androidApplication(), get()) }
    viewModel { NotifyViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { ChatRoomViewModel(androidApplication(), get(), get()) }
    viewModel { ChatRoomAddViewModel() }
    viewModel { MediaViewModel(get()) }
    viewModel { MediaPhotoViewModel() }
}
