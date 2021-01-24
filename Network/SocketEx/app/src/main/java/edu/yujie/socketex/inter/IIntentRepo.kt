package edu.yujie.socketex.inter

import android.content.Context
import android.net.Uri
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.IntentBuilder
import edu.yujie.socketex.bean.IntentResult
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

abstract class IIntentRepo {

    abstract fun createCameraBuilder(context: Context): Observable<IntentBuilder>

    abstract fun onCameraResult(result: IntentResult): Observable<IntentResult>

    abstract fun createCropBuilder(uri: Uri): Observable<IntentBuilder>

    abstract fun onCropResult(result: IntentResult): Observable<IntentResult>

    abstract fun createAlbumBuilder(): Observable<IntentBuilder>

    abstract fun onAlbumResult(result: IntentResult): Observable<IntentResult>

    
//    //-------------------------------------------------------------
//    abstract fun prepareRecording(context: Context): Completable
//
//    abstract fun startRecording()
//
//    abstract fun stopRecording(): Completable
//
//    abstract val stateRe: BehaviorRelay<Boolean>
//
//    abstract val lessTimeRelay: BehaviorRelay<Boolean>

    

}