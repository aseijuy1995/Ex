package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderResult
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IMediaRecorderModule
import io.reactivex.rxjava3.disposables.Disposable

class RecordingRepoImpl(private val iMediaRecorderModule: IMediaRecorderModule) : IRecordingRepo {

    override val stateRelay: BehaviorRelay<Boolean>
        get() = iMediaRecorderModule.stateRelay

    override val resultRelay: PublishRelay<Pair<Boolean, RecorderResult?>>
        get() = iMediaRecorderModule.resultRelay

    override val lengthTimeRelay: PublishRelay<Int>
        get() = iMediaRecorderModule.lengthTimeRelay

    override fun prepare(setting: RecorderSetting): Disposable = iMediaRecorderModule.prepare(setting).subscribe()

    override fun start() = iMediaRecorderModule.start()

    override fun stop(): Disposable = iMediaRecorderModule.stop().subscribe()

}