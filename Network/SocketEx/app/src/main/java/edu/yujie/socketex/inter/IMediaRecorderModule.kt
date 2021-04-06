package edu.yujie.socketex.inter

import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

interface IMediaRecorderModule {

//    val stateRelay: BehaviorRelay<Boolean>//錄音狀態
//
//    val resultRelay: PublishRelay<Pair<Boolean, RecorderResult?>>//返回result
//
//    val lengthTimeRelay: PublishRelay<Int>//錄音時間

    fun prepare(setting: RecorderSetting): Completable

    fun start()

    fun stop()

}