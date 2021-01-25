package edu.yujie.socketex.impl

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderResult
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File

interface IRecordingRepo {

    val stateRelay: BehaviorRelay<Boolean>

    val resultRelay: PublishRelay<Pair<Boolean, RecorderResult?>>

    val lengthTimeRelay: PublishRelay<Int>

    fun prepare(setting: RecorderSetting): Disposable

    fun start()

    fun stop() : Disposable
}