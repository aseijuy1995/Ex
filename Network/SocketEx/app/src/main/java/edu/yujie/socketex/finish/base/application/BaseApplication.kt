package edu.yujie.socketex.finish.base.application

import android.app.Application
import edu.yujie.socketex.LogTree
import edu.yujie.socketex.album.AlbumRepowImpl
import edu.yujie.socketex.album.IAlbumRepow
import edu.yujie.socketex.const.IApiService
import edu.yujie.socketex.impl.*
import edu.yujie.socketex.inter.*
import edu.yujie.socketex.listener.MediaRepo
import edu.yujie.socketex.repo.ApiRepo
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.util.RetrofitManager
import edu.yujie.socketex.vm.ChatRoomViewModel
import edu.yujie.socketex.vm.MediaViewModel
import edu.yujie.socketex.vm.StartVM
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber


class BaseApplication : Application() {
    private val utilModules = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
        single<RetrofitManager> { RetrofitManager.get("", (get() as OkHttpUtil).client) }
        single<IApiService> { RetrofitManager.create<IApiService>() }
    }

    private val repoModules = module {
        single<IApiRepo> { ApiRepo() }

        single<IIntentRepo> { IntentRepoImpl() }
        single<IAlbumRepow> { AlbumRepowImpl(androidContext()) }
    }


    private val modelModules = module {
        single<ICamera> { CameraImpl() }
        single<IAlbum> { AlbumImpl() }
        single<ICrop> { CropImpl() }
        single<IMediaRepo> { MediaRepo(androidContext()) }
        //
        single<IRecording> { RecordingImpl() }
        single<IRecordingRepo> { RecordingRepoImpl(get()) }
    }


    private val viewModules = module {
        viewModel<StartVM> { StartVM(get()) }
        viewModel { ChatRoomViewModel(this@BaseApplication, get(), get()) }
        viewModel<MediaViewModel> { MediaViewModel(androidApplication(), get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.ERROR)
            modules(modelModules, utilModules, repoModules, viewModules)
        }

        Timber.plant(LogTree)
    }
}