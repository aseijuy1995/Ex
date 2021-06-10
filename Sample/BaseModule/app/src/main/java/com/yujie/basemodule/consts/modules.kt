//package tw.north27.coachingapp.consts
//
//import com.yujie.utilmodule.http.OkHttpUtil
//import com.yujie.utilmodule.http.RetrofitManager
//import org.koin.android.ext.koin.androidApplication
//import org.koin.android.ext.koin.androidContext
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.qualifier.named
//import org.koin.dsl.module
//import tw.north27.coachingapp.BuildConfig
//import tw.north27.coachingapp.chat.*
//import tw.north27.coachingapp.media.*
//import tw.north27.coachingapp.media.mediaCodec.IMediaExtractorModule
//import tw.north27.coachingapp.media.mediaCodec.MediaExtractorModule
//import tw.north27.coachingapp.media.mediaStore.AudioMediaStoreModule
//import tw.north27.coachingapp.media.mediaStore.IMediaStoreModule
//import tw.north27.coachingapp.media.mediaStore.ImageMediaStoreModule
//import tw.north27.coachingapp.media.mediaStore.VideoMediaStoreModule
//import tw.north27.coachingapp.notify.INotifyRepository
//import tw.north27.coachingapp.notify.NotifyRepository
//import tw.north27.coachingapp.notify.NotifyViewModel
//import tw.north27.coachingapp.repository.PublicRepository
//import tw.north27.coachingapp.repository.inter.IPublicRepository
//import tw.north27.coachingapp.repository.nofinish.IUserRepository
//import tw.north27.coachingapp.repository.nofinish.UserRepository
//import tw.north27.coachingapp.viewModel.MainHomeViewModel
//import tw.north27.coachingapp.viewModel.SignInViewModel
//import tw.north27.coachingapp.viewModel.SignOutViewModel
//import tw.north27.coachingapp.viewModel.StartViewModel
//
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
//
//val modelModules = module {
////    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
//    single<IApiService> { ApiService(androidContext()) }
//}
//
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
//
//val viewModelModules = module {
//    viewModel { StartViewModel(androidApplication(), get(), get()) }
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
//}
