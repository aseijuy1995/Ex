package tw.north27.coachingapp.consts

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.repository.*
import tw.north27.coachingapp.viewModel.*

//val utilModules = module {
//    single<OkHttpUtil> { OkHttpUtil(androidContext()) }
//    single<RetrofitManager> { RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client) }
//
//
//
//    single<IChatModule> { ChatModule(get()) }
//    single<IMediaStoreModule>(named("image")) { ImageMediaStoreModule(androidContext()) }
//    single<IMediaStoreModule>(named("video")) { VideoMediaStoreModule(androidContext()) }
//    single<IMediaStoreModule>(named("audio")) { AudioMediaStoreModule(androidContext()) }
//    single<IAudioMediaCodecModule> { AudioMediaCodecModule(androidContext()) }
//    single<IMediaRecorderModule> { MediaRecorderModule() }
//    factory<IMediaExtractorModule> { MediaExtractorModule() }
//
//}

val modelModules = module {
    single<IApiService> { ApiService(androidContext()) }
//    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
}

val repoModules = module {
    single<IPublicRepository> { PublicRepository(get()) }
    single<IUserRepository> { UserRepository(get(), androidContext()) }
    single<IActionRepository> { ActionRepository(get()) }

//    single<INotifyRepository> { NotifyRepository(get()) }
//    single<IChatRepository> { ChatRepository(get(), get()) }
//    single<IMediaRepository> {
//        MediaRepository(
//            get<IMediaStoreModule>(named("image")),
//            get<IMediaStoreModule>(named("video")),
//            get<IMediaStoreModule>(named("audio")),
//            get<IAudioMediaCodecModule>(),
//            get<IMediaRecorderModule>()
//        )
//    }

}

val viewModelModules = module {
    viewModel<PublicViewModel> { PublicViewModel(androidApplication(), get(), get()) }
    viewModel<PersonalViewModel> { PersonalViewModel(androidApplication(), get(), get()) }
    viewModel<StartViewModel> { StartViewModel(androidApplication(), get() as IPublicRepository, get() as IUserRepository) }
    viewModel<SignInViewModel> { SignInViewModel(androidApplication(), get()) }
    viewModel<SignOutViewModel> { SignOutViewModel(androidApplication(), get()) }
    //
    viewModel<MainHomeViewModel> { MainHomeViewModel(androidApplication(), get(), get()) }
    //

//    //
//    viewModel { NotifyViewModel(get()) }
//    viewModel { ChatViewModel(get()) }
//    viewModel { ChatRoomViewModel(androidApplication(), get(), get()) }
//    viewModel { ChatRoomAddViewModel() }
//    viewModel { MediaViewModel(get()) }
//    viewModel { MediaPhotoViewModel() }

}
