package tw.north27.coachingapp.consts

import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.viewModel.StartViewModel

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

//val modelModules = module {
////    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
//    single<IApiService> { ApiService(androidContext()) }
//}

//val repoModules = module {
//    single<IPublicRepository> { PublicRepository(get()) }
//    single<IUserRepository> { UserRepository(get(), androidContext()) }
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
//
//}

val viewModelModules = module {
    viewModel { StartViewModel(androidApplication(), get(), get()) }
//    viewModel { SignInViewModel(androidApplication(), get()) }
//    viewModel { MainHomeViewModel(androidApplication(), get()) }
//    //
//    viewModel { NotifyViewModel(get()) }
//    viewModel { ChatViewModel(get()) }
//    viewModel { ChatRoomViewModel(androidApplication(), get(), get()) }
//    viewModel { ChatRoomAddViewModel() }
//    viewModel { MediaViewModel(get()) }
//    viewModel { MediaPhotoViewModel() }
//    viewModel { SignOutViewModel(androidApplication(), get()) }
}
