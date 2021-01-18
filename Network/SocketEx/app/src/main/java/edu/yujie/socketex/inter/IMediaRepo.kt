package edu.yujie.socketex.inter

import android.content.Context
import android.net.Uri
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

abstract class IMediaRepo {

    abstract fun createCameraBuilder(context: Context): Observable<IntentBuilder>

    abstract fun onCameraResult(result: IntentResult): Observable<IntentResult>

    abstract fun createCropBuilder(uri: Uri): Observable<IntentBuilder>

    abstract fun onCropResult(result: IntentResult): Observable<IntentResult>

    abstract fun createAlbumBuilder(): Observable<IntentBuilder>

    abstract fun onAlbumResult(result: IntentResult): Observable<IntentResult>

    abstract fun prepareRecording(context: Context): Completable

    abstract val recordingStateRelay: BehaviorRelay<Boolean>

    abstract val enoughRecordingTimeRelay: BehaviorRelay<Boolean>

    abstract fun startRecording(): Observable<Long>

    abstract fun stopRecording(): Completable

}