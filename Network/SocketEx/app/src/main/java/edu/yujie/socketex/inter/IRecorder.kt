package edu.yujie.socketex.inter

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable
import java.io.File

interface IRecorder {

    val stateRelay: BehaviorRelay<Boolean>//錄音狀態

    val doneRelay: PublishRelay<Pair<Boolean, File?>>//是否完成/recorder path

    val recordingTimeRelay: PublishRelay<Int>//錄音時間

    fun prepareRecording(setting: RecorderSetting): Completable

    fun startRecording()

    fun stopRecording(): Completable

}