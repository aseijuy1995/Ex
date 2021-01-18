package edu.yujie.socketex.inter

import android.content.Context
import android.net.Uri
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import io.reactivex.rxjava3.core.Observable

abstract class IMediaRepo {

    abstract fun createCameraBuilder(context: Context): Observable<IntentBuilder>

    abstract fun onCameraResult(result: IntentResult): Observable<IntentResult>

    abstract fun createCropBuilder(uri: Uri): Observable<IntentBuilder>

    abstract fun onCropResult(result: IntentResult): Observable<IntentResult>

    abstract fun createAlbumBuilder(): Observable<IntentBuilder>

    abstract fun onAlbumResult(result: IntentResult): Observable<IntentResult>

    abstract fun startRecorder(context:Context): Observable<Long>

    abstract fun stopRecorder(): Observable<Int>

}