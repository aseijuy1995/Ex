package edu.yujie.socketex.base

import android.app.Application
import edu.yujie.socketex.LogTree
import edu.yujie.socketex.album.AlbumRepowImpl
import edu.yujie.socketex.album.IAlbumRepow
import edu.yujie.socketex.impl.*
import edu.yujie.socketex.inter.*
import edu.yujie.socketex.listener.MediaRepo
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.vm.ChatRoomViewModel
import edu.yujie.socketex.vm.MediaViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber


class BaseApplication : Application() {
    private val modelModules = module {
        single<ICamera> { CameraImpl() }
        single<IAlbum> { AlbumImpl() }
        single<ICrop> { CropImpl() }
        single<IMediaRepo> { MediaRepo(androidContext()) }
        //
        single<IRecorder> { RecorderImpl() }
        single<IRecorderRepo> { RecorderRepoImpl(get()) }
    }

    private val utilModules = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
    }

    private val repoModules = module {
        single<IIntentRepo> { IntentRepoImpl() }
        single<IAlbumRepow> { AlbumRepowImpl(androidContext()) }
    }
    private val viewModules = module {
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