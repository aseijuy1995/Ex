package edu.yujie.socketex.impl

import android.content.Context
import android.net.Uri
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import edu.yujie.socketex.bean.IntentSetting
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject


class MediaRepoImpl : IMediaRepo(), KoinComponent {
    private val camera by inject<ICamera>()
    private val crop by inject<ICrop>()
    private val album by inject<IAlbum>()
    private val recorder by inject<IRecorder>()

    //camera
    override fun createCameraBuilder(context: Context): Observable<IntentBuilder> = camera.createCameraBuilder(IntentSetting.CameraSetting(context = context))

    override fun onCameraResult(result: IntentResult): Observable<IntentResult> = camera.onCameraResult(result = result)

    //crop
    override fun createCropBuilder(uri: Uri): Observable<IntentBuilder> = crop.createCropBuilder(IntentSetting.CropSetting(uri = uri))

    override fun onCropResult(result: IntentResult): Observable<IntentResult> = crop.onCropResult(result = result)

    //album
    override fun createAlbumBuilder() = album.createAlbumBuilder(IntentSetting.AlbumSetting)

    override fun onAlbumResult(result: IntentResult): Observable<IntentResult> = album.onAlbumResult(result = result)

    //recorder
    override fun startRecorder(context: Context): Observable<Long> {
        return recorder.initRecorder(RecorderSetting(context = context))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .mergeWith {
                recorder.startRecorder(context = context)
            }.toObservable<Long>()
    }

    override fun stopRecorder(): Observable<Int> {
        TODO("Not yet implemented")
    }


}