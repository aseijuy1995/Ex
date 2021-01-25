package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IRecorder
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File

class RecorderRepoImpl(private val iRecorder: IRecorder) : IRecorderRepo {

    override val stateRelay: BehaviorRelay<Boolean>
        get() = iRecorder.stateRelay

    override val doneRelay: PublishRelay<Pair<Boolean, File?>>
        get() = iRecorder.doneRelay

    override val recordingTimeRelay: PublishRelay<Int>
        get() = iRecorder.recordingTimeRelay

    override fun prepareRecording(setting: RecorderSetting): Disposable = iRecorder.prepareRecording(setting).subscribe()

    override fun startRecording() = iRecorder.startRecording()

    override fun stopRecording(): Disposable = iRecorder.stopRecording().subscribe()

}