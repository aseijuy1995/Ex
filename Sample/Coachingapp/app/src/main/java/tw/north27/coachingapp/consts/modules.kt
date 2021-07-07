package tw.north27.coachingapp.consts

import com.yujie.utilmodule.http.*
import com.yujie.utilmodule.pref.getRefreshToken
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.repository.*
import tw.north27.coachingapp.viewModel.*

val httpModules = module {
    single<OkHttpUtil.Entity> {
        OkHttpUtil.get(
            OkHttpConfig(
                authReqIntcp = AuthRequestInterceptor(
                    cxt = androidContext(),
                    refreshTokenCallback = get()
                ),
                authRspIntcp = AuthResponseInterceptor(cxt = androidContext(), refreshTokenCallback = {
                    val refreshToken = runBlocking { androidContext().userPref.getRefreshToken().first() }
                    (get() as IApiService).refreshToken(refreshToken)
                }),
                refTokenRspIntcp = get()
            )
        )
    }

    single<ReAuthResponseInterceptor> {
        ReAuthResponseInterceptor {
            //FIXME 處理refreshToken失效
        }
    }

    single<RetrofitManager.Entity> {
        RetrofitManager.get(
            RetrofitConfig(
                baseUrl = BuildConfig.BASE_URL,
                entity = (get() as OkHttpUtil.Entity),
                converterFactory = ConverterFactory.GsonFactory
            )
        )
    }

    single<IApiService> { ApiService(androidContext()) }
//    single<IApiService> { (get() as RetrofitManager.Entity).create<IApiService>() }
}

val modelModules = module {
//    single<IChatModule> { ChatModule(get()) }
//    single<IMediaStoreModule>(named("image")) { ImageMediaStoreModule(androidContext()) }
//    single<IMediaStoreModule>(named("video")) { VideoMediaStoreModule(androidContext()) }
//    single<IMediaStoreModule>(named("audio")) { AudioMediaStoreModule(androidContext()) }
//    single<IAudioMediaCodecModule> { AudioMediaCodecModule(androidContext()) }
//    single<IMediaRecorderModule> { MediaRecorderModule() }
//    factory<IMediaExtractorModule> { MediaExtractorModule() }
}

val repoModules = module {
    single<IPublicRepository> { PublicRepository(get()) }
    single<IUserRepository> { UserRepository(get()) }
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
    viewModel<StartViewModel> { StartViewModel(androidApplication(), get()) }
    viewModel<SignInViewModel> { SignInViewModel(androidApplication(), get()) }
    //
    viewModel<AskViewModel> { AskViewModel(androidApplication(), get()) }
    viewModel<AskRoomViewModel> { AskRoomViewModel(get(), get()) }
    //
    viewModel<SignOutViewModel> { SignOutViewModel(androidApplication(), get()) }
    viewModel<CoachingViewModel> { CoachingViewModel(androidApplication(), get()) }
    viewModel<PersonalViewModel> { PersonalViewModel(androidApplication(), get(), get()) }
    //


    //
    //

//    //
//    viewModel { NotifyViewModel(get()) }
//    viewModel { ChatViewModel(get()) }
//    viewModel { ChatRoomViewModel(androidApplication(), get(), get()) }
//    viewModel { ChatRoomAddViewModel() }
//    viewModel { MediaViewModel(get()) }
//    viewModel { MediaPhotoViewModel() }

}
