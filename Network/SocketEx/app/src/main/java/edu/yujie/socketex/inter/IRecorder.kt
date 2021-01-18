package edu.yujie.socketex.inter

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

interface IRecorder {

    val recordingStateRelay: BehaviorRelay<Boolean>

    val enoughRecordingTimeRelay: BehaviorRelay<Boolean>

    fun prepareRecording(setting: RecorderSetting): Completable

    fun startRecording(): PublishRelay<Long>

    fun stopRecording(): Completable

}