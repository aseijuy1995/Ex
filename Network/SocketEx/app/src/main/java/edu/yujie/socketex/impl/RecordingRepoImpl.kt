package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderResult
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IRecording
import io.reactivex.rxjava3.disposables.Disposable

class RecordingRepoImpl(private val iRecording: IRecording) : IRecordingRepo {

    override val stateRelay: BehaviorRelay<Boolean>
        get() = iRecording.stateRelay

    override val resultRelay: PublishRelay<Pair<Boolean, RecorderResult?>>
        get() = iRecording.resultRelay

    override val lengthTimeRelay: PublishRelay<Int>
        get() = iRecording.lengthTimeRelay

    override fun prepare(setting: RecorderSetting): Disposable = iRecording.prepare(setting).subscribe()

    override fun start() = iRecording.start()

    override fun stop(): Disposable = iRecording.stop().subscribe()

}