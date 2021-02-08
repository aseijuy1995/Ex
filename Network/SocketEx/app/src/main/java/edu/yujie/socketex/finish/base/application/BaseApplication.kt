package edu.yujie.socketex.finish.base.application

import android.app.Application
import android.os.StrictMode
import androidx.lifecycle.ProcessLifecycleOwner
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.BuildConfig
import edu.yujie.socketex.album.AlbumRepowImpl
import edu.yujie.socketex.album.IAlbumRepow
import edu.yujie.socketex.const.ApiService
import edu.yujie.socketex.const.IApiService
import edu.yujie.socketex.finish.data.IPreferenceDataStoreStorage
import edu.yujie.socketex.finish.data.PreferenceDataStoreStorage
import edu.yujie.socketex.finish.inter.IApiRepo
import edu.yujie.socketex.finish.inter.IUserRepo
import edu.yujie.socketex.finish.repo.ApiRepo
import edu.yujie.socketex.finish.repo.UserRepo
import edu.yujie.socketex.finish.util.LogTree
import edu.yujie.socketex.finish.util.OkHttpUtil
import edu.yujie.socketex.finish.util.ProcessLifeObs
import edu.yujie.socketex.finish.util.RetrofitManager
import edu.yujie.socketex.finish.vm.ChatRoomViewModel
import edu.yujie.socketex.finish.vm.MediaViewModel
import edu.yujie.socketex.finish.vm.StartViewModel
import edu.yujie.socketex.impl.*
import edu.yujie.socketex.inter.*
import edu.yujie.socketex.listener.MediaRepo
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber


class BaseApplication : Application() {

    companion object {
        lateinit var appForegroundRelay: PublishRelay<Boolean>
    }

    private val utilModules = module {
        single<OkHttpUtil> { OkHttpUtil.get(androidContext()) }
        single<RetrofitManager> { RetrofitManager.get("", (get() as OkHttpUtil).client) }
//        single<IApiService> { RetrofitManager.create<IApiService>() }
        single<IApiService> { RetrofitManager.create<ApiService>() }
    }

    private val modelModules = module {
        single<IPreferenceDataStoreStorage> { PreferenceDataStoreStorage(androidContext()) }
        //
        //
        //
        single<ICamera> { CameraImpl() }
        single<IAlbum> { AlbumImpl() }
        single<ICrop> { CropImpl() }
        single<IMediaRepo> { MediaRepo(androidContext()) }
        //
        single<IRecording> { RecordingImpl() }
        single<IRecordingRepo> { RecordingRepoImpl(get()) }
    }

    private val repoModules = module {
        single<IApiRepo> { ApiRepo(get()) }
        single<IUserRepo> { UserRepo(get()) }
        //
        single<IIntentRepo> { IntentRepoImpl() }
        single<IAlbumRepow> { AlbumRepowImpl(androidContext()) }
    }

    private val viewModules = module {
        viewModel<StartViewModel> { StartViewModel(androidApplication(), get(), get()) }
        viewModel<ChatRoomViewModel> { ChatRoomViewModel(androidApplication(), get(), get()) }
        viewModel<MediaViewModel> { MediaViewModel(androidApplication(), get()) }
    }


    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().apply {
                    detectAll()
                    detectCustomSlowCalls()
                    detectDiskReads()
                    detectDiskWrites()
                    detectNetwork()
                    detectResourceMismatches()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectUnbufferedIo()
                    penaltyDialog()
//                    penaltyDropBox()
                    penaltyFlashScreen()
                    penaltyLog()
//                    penaltyDeath()
                }.build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder().apply {
                    detectActivityLeaks()
                    detectAll()
                    detectCleartextNetwork()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectContentUriWithoutPermission()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectCredentialProtectedWhileLocked()
                    detectFileUriExposure()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) detectImplicitDirectBoot()
                    detectLeakedClosableObjects()
                    detectLeakedRegistrationObjects()
                    detectLeakedSqlLiteObjects()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) detectNonSdkApiUsage()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) detectUntaggedSockets()
                    penaltyLog()
//                    setClassInstanceLimit()
//                    penaltyDeath()
                }.build()
            )
        }
        super.onCreate()
        //Timber
        Timber.plant(LogTree)
        //ProcessLifecycleObserver
        ProcessLifeObs(ProcessLifecycleOwner.get()).apply {
            BaseApplication.appForegroundRelay = appForegroundRelay
        }
        //Koin
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.ERROR)
            modules(modelModules, utilModules, repoModules, viewModules)
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_RUNNING_MODERATE -> Timber.d("記憶體不足（後台程序超過5個），並且該程序優先順序比較高，需要清理記憶體。")
            TRIM_MEMORY_RUNNING_LOW -> Timber.d("記憶體不足（後台程序不足5個），和該程序優先順序比較高，需要清理記憶體。")
            TRIM_MEMORY_RUNNING_CRITICAL -> Timber.d("記憶體不足(後臺程序不足3個)，並且該程序優先順序比較高，需要清理記憶體。")
            //
            TRIM_MEMORY_BACKGROUND -> Timber.d("記憶體不足，並且該程序是後臺程序。")
            TRIM_MEMORY_MODERATE -> Timber.d("記憶體不足，並且該程序在後臺程序列表的中部。")
            TRIM_MEMORY_COMPLETE -> Timber.d("記憶體不足，並且該程序在後臺程序列表最後一個，馬上就要被清理。")
            //
            TRIM_MEMORY_UI_HIDDEN -> Timber.d("記憶體不足，並且該程序的UI已經不可見了。")
        }
    }
}