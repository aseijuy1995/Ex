package tw.north27.coachingapp.consts

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tw.north27.coachingapp.BuildConfig
import tw.north27.coachingapp.chat.*
import tw.north27.coachingapp.media.*
import tw.north27.coachingapp.media.mediaCodec.IMediaCodecModule
import tw.north27.coachingapp.media.mediaCodec.IMediaExtractorModule
import tw.north27.coachingapp.media.mediaCodec.MediaExtractorModule
import tw.north27.coachingapp.media.mediaCodec.VideoCompressModule
import tw.north27.coachingapp.media.mediaStore.AudioMediaStoreModule
import tw.north27.coachingapp.media.mediaStore.IMediaStoreModule
import tw.north27.coachingapp.media.mediaStore.ImageMediaStoreModule
import tw.north27.coachingapp.media.mediaStore.VideoMediaStoreModule
import tw.north27.coachingapp.module.http.OkHttpUtil
import tw.north27.coachingapp.module.http.RetrofitManager
import tw.north27.coachingapp.notify.INotifyRepository
import tw.north27.coachingapp.notify.NotifyRepository
import tw.north27.coachingapp.notify.NotifyViewModel
import tw.north27.coachingapp.repository.PublicRepository
import tw.north27.coachingapp.repository.UserRepository
import tw.north27.coachingapp.repository.inter.IPublicRepository
import tw.north27.coachingapp.repository.inter.IUserRepository
import tw.north27.coachingapp.ui.public.ExitViewModel
import tw.north27.coachingapp.viewModel.SignInViewModel
import tw.north27.coachingapp.viewModel.StartViewModel

val utilModules = module {
    single<OkHttpUtil> { OkHttpUtil(androidContext()) }
    single<RetrofitManager> { RetrofitManager.get(BuildConfig.BASE_URL, (get() as OkHttpUtil).client) }
    single<IChatModule> { ChatModule(get()) }
    single<IMediaStoreModule>(named("image")) { ImageMediaStoreModule(androidContext()) }
    single<IMediaStoreModule>(named("video")) { VideoMediaStoreModule(androidContext()) }
    single<IMediaStoreModule>(named("audio")) { AudioMediaStoreModule(androidContext()) }
    single<IAudioMediaCodecModule> { AudioMediaCodecModule(androidContext()) }
    single<IMediaRecorderModule> { MediaRecorderModule() }
    single<IMediaExtractorModule> { MediaExtractorModule() }
    single<IMediaCodecModule>(named("video")) { VideoCompressModule(get()) }

}

val modelModules = module {
//    single<IApiService> { (get() as RetrofitManager).create<IApiService>() }
    single<IApiService> { ApiService(androidContext()) }
}

val repoModules = module {
    single<IPublicRepository> { PublicRepository(get()) }
    single<IUserRepository> { UserRepository(get(), androidContext()) }
    single<INotifyRepository> { NotifyRepository(get()) }
    single<IChatRepository> { ChatRepository(get(), get()) }
    single<IMediaRepository> {
        MediaRepository(
            get<IMediaStoreModule>(named("image")),
            get<IMediaStoreModule>(named("video")),
            get<IMediaStoreModule>(named("audio")),
            get<IAudioMediaCodecModule>(),
            get<IMediaRecorderModule>()
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
    viewModel { ExitViewModel(androidApplication(), get()) }
}
