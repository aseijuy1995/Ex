package edu.yujie.socketex.inter

import android.content.Context
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface IRecorder {

    fun initRecorder(setting: RecorderSetting): Completable

    fun startRecorder(context: Context): Observable<Long>

    fun stopRecorder(): BehaviorRelay<Boolean>

}