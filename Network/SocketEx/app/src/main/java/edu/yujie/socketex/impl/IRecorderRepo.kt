package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File

interface IRecorderRepo {

    val stateRelay: BehaviorRelay<Boolean>

    val doneRelay: PublishRelay<Pair<Boolean,File?>>

    val recordingTimeRelay: PublishRelay<Int>

    fun prepareRecording(setting: RecorderSetting): Disposable

    fun startRecording()

    fun stopRecording() : Disposable
}