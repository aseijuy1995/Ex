package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IRecorder
import io.reactivex.rxjava3.disposables.Disposable

interface IRecorderRepo {

    val stateRelay: BehaviorRelay<Boolean>

    val lessTimeRelay: BehaviorRelay<Boolean>

    val recordingTimeRelay: PublishRelay<Int>

    fun prepareRecording(setting: RecorderSetting): Disposable

    fun startRecording()

    fun stopRecording(): Disposable
}

class RecorderRepoImpl(private val iRecorder: IRecorder) : IRecorderRepo {

    override val stateRelay: BehaviorRelay<Boolean>
        get() = iRecorder.stateRelay

    override val lessTimeRelay: BehaviorRelay<Boolean>
        get() = iRecorder.lessTimeRelay

    override val recordingTimeRelay: PublishRelay<Int>
        get() = iRecorder.recordingTimeRelay

    override fun prepareRecording(setting: RecorderSetting) = iRecorder.prepareRecording(setting).subscribe()

    override fun startRecording() = iRecorder.startRecording()

    override fun stopRecording() = iRecorder.stopRecording().subscribe()

}