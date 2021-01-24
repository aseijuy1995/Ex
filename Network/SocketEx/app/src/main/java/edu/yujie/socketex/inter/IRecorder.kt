package edu.yujie.socketex.inter

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

interface IRecorder {

    val stateRelay: BehaviorRelay<Boolean>

    val lessTimeRelay: BehaviorRelay<Boolean>

    val recordingTimeRelay: PublishRelay<Int>

    fun prepareRecording(setting: RecorderSetting): Completable

    fun startRecording()

    fun stopRecording(): Completable

}