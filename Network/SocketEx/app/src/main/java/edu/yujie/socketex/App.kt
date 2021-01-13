package edu.yujie.socketex

import android.app.Application
import edu.yujie.socketex.album.AlbumRepoImpl
import edu.yujie.socketex.album.GalleryViewModel
import edu.yujie.socketex.album.IAlbumRepo
import edu.yujie.socketex.util.OkHttpUtil
import edu.yujie.socketex.vm.ChatRoomViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber


class App : Application() {
    private val module = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
        viewModel { ChatRoomViewModel(this@App) }
        //
        single<IAlbumRepo> { AlbumRepoImpl(androidContext()) }
        viewModel<GalleryViewModel> { GalleryViewModel() }
    }

    companion object {
        val REQUEST_CODE_CAPTURE = 1001//拍照
        val REQUEST_CODE_ALBUM = 1002//相簿
        val REQUEST_CODE_AUDIO = 1003//音訊
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(module)
        }

        Timber.plant(LogTree)
//        Logger.addLogAdapter(AndroidLogAdapter())
    }
}