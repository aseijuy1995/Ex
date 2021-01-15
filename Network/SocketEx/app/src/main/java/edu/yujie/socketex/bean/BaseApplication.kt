package edu.yujie.socketex.bean

import android.app.Application
import edu.yujie.socketex.LogTree
import edu.yujie.socketex.`interface`.IIntentRepo
import edu.yujie.socketex.`interface`.IRecorder
import edu.yujie.socketex.album.AlbumRepowImpl
import edu.yujie.socketex.album.GalleryViewModel
import edu.yujie.socketex.album.IAlbumRepow
import edu.yujie.socketex.impl.IntentRepoImpl
import edu.yujie.socketex.impl.RecorderImpl
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber


class BaseApp : Application() {
    private val utilModules = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
    }

    private val repoModules = module {
        single<IIntentRepo> { IntentRepoImpl() }
        single<IRecorder> { RecorderImpl() }
        single<IAlbumRepow> { AlbumRepowImpl(androidContext()) }
    }
    private val viewModules = module {
        viewModel { ChatRoomViewModel(this@BaseApp, get(), get()) }
        viewModel<GalleryViewModel> { GalleryViewModel() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApp)
            androidLogger(Level.ERROR)
            modules(utilModules, repoModules, viewModules)
        }

        Timber.plant(LogTree)
    }
}