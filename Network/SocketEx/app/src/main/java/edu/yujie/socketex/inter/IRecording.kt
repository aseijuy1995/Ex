package edu.yujie.socketex.inter

import android.media.MediaRecorder
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderResult
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

interface IRecording {

    val stateRelay: BehaviorRelay<Boolean>//錄音狀態

    val resultRelay: PublishRelay<Pair<Boolean, RecorderResult?>>//返回result

    val lengthTimeRelay: PublishRelay<Int>//錄音時間

    fun buildRecorder(setting: RecorderSetting): MediaRecorder

    fun prepare(setting: RecorderSetting): Completable

    fun start()

    fun stop(): Completable

}