package edu.yujie.socketex.impl

import android.content.Context
import android.net.Uri
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.inter.*
import io.reactivex.rxjava3.core.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject


class IntentRepoImpl : IIntentRepo(), KoinComponent {
    private val camera by inject<ICamera>()
    private val crop by inject<ICrop>()
    private val album by inject<IAlbum>()
    private val recorder by inject<IMediaRecorderModule>()

    //camera
    override fun createCameraBuilder(context: Context): Observable<IntentBuilder> = camera.createCameraBuilder(IntentSetting.CameraSetting(context = context))

    override fun onCameraResult(result: IntentResult): Observable<IntentResult> = camera.onCameraResult(result = result)

    //crop
    override fun createCropBuilder(uri: Uri): Observable<IntentBuilder> = crop.createCropBuilder(IntentSetting.CropSetting(uri = uri))

    override fun onCropResult(result: IntentResult): Observable<IntentResult> = crop.onCropResult(result = result)

    //album
    override fun createAlbumBuilder() = album.createAlbumBuilder(IntentSetting.AlbumSetting)

    override fun onAlbumResult(result: IntentResult): Observable<IntentResult> = album.onAlbumResult(result = result)

//    //recorder
//    /**
//     * Note: On devices running Android 9 (API level 28) or higher, apps running in the background cannot access the microphone.
//     * Therefore, your app should record audio only when it's in the foreground or when you include an instance of MediaRecorder in a foreground service.
//     * */
//    override fun prepareRecording(context: Context): Completable = recorder.prepareRecording(setting = RecorderSetting(context = context))
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    override val stateRe: BehaviorRelay<Boolean>
//        get() = recorder.stateRelay
//
//    override val lessTimeRelay: BehaviorRelay<Boolean>
//        get() = recorder.lessTimeRelay
//
//
//    override fun startRecording(): Observable<Long> = recorder.startRecording()
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    override fun stopRecording(): Completable = recorder.stopRecording()
//        .subscribeOn(AndroidSchedulers.mainThread())


}