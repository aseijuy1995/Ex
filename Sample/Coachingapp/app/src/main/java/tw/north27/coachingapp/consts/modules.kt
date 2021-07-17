package tw.north27.coachingapp.consts

import com.yujie.core_lib.http.ConverterFactory
import com.yujie.core_lib.http.okhttp.AuthResponseInterceptor
import com.yujie.core_lib.http.okhttp.BearerAuthRequestInterceptor
import com.yujie.core_lib.http.okhttp.OkHttpConfig
import com.yujie.core_lib.http.okhttp.OkHttpManager
import com.yujie.core_lib.http.retrofit.RetrofitConfig
import com.yujie.core_lib.http.retrofit.RetrofitManager
import com.yujie.core_lib.model.IMediaStoreModule
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.getRefreshToken
import com.yujie.core_lib.pref.userPref
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.model.request.TokenRequest
import tw.north27.coachingapp.repository.*
import tw.north27.coachingapp.viewModel.*

val httpModules = module {
    single<OkHttpManager.Entity> {
        OkHttpManager.get(
            OkHttpConfig(
                authReqIntcp = BearerAuthRequestInterceptor(
                    cxt = androidContext(),
                    expiredInCallback = {
                        val refreshToken = runBlocking { androidContext().userPref.getRefreshToken().first() }
                        (get() as IUserRepository).refreshToken(
                            tokenRequest = TokenRequest(
                                clientId = runBlocking { androidContext().userPref.getId().first() },
                                refreshToken = refreshToken,
                            )
                        )
                    }
                ),
                authRspIntcp = AuthResponseInterceptor(cxt = androidContext(),
                    callback401 = {
                        val refreshToken = runBlocking { androidContext().userPref.getRefreshToken().first() }
                        (get() as IUserRepository).refreshToken(
                            tokenRequest = TokenRequest(
                                clientId = runBlocking { androidContext().userPref.getId().first() },
                                refreshToken = refreshToken,
                            )
                        )
                    },
                    callback410 = {
                        get()
                    }
                ),
            )
        )
    }


    single<RetrofitManager.Entity> {
        RetrofitManager.get(
            RetrofitConfig(
                baseUrl = BuildConfig.BASE_URL,
                entity = (get() as OkHttpManager.Entity),
                converterFactory = ConverterFactory.GsonFactory
            )
        )
    }

    single<IApiService> { ApiService(androidContext()) }
//    single<IApiService> { (get() as RetrofitManager.Entity).create<IApiService>() }
}

val moduleModules = module {
//    single<IChatModule> { ChatModule(get()) }
//    single<IMediaStoreModule> { ImageMediaStoreModule(androidContext()) }
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
    single<IMediaRepository> {
        MediaRepository(
            get<IMediaStoreModule>(),
            //
//            get<IMediaStoreModule>(named("video")),
//            get<IMediaStoreModule>(named("audio")),
//            get<IAudioMediaCodecModule>(),
//            get<IMediaRecorderModule>()
        )
    }

}

val viewModelModules = module {
    viewModel<PublicViewModel> { PublicViewModel(androidApplication(), get(), get()) }
    viewModel<StartViewModel> { StartViewModel(androidApplication(), get()) }
    viewModel<SignInViewModel> { SignInViewModel(androidApplication(), get()) }
    //
    viewModel<AskViewModel> { AskViewModel(androidApplication(), get()) }
    viewModel<EducationSelectorViewModel> { EducationSelectorViewModel(androidApplication(), get()) }
    viewModel<AskRoomViewModel> { AskRoomViewModel(androidApplication(), get(), get()) }
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
