package edu.yujie.socketex.`interface`

import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

interface IRecorder {

    fun buildRecorder(setting: RecorderSetting): Completable

    fun startRecorder(): BehaviorRelay<Boolean>

    fun stopRecorder()

    fun clearRecorder(): Completable

}