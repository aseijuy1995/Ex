package edu.yujie.socketex.impl

import android.media.MediaRecorder
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.`interface`.IRecorder
import edu.yujie.socketex.bean.RecorderSetting
import io.reactivex.rxjava3.core.Completable

class RecorderImpl : IRecorder {
    private var mediaRecorder: MediaRecorder? = null

    val insuffectTimeRelay = BehaviorRelay.createDefault<Boolean>(false)

    private val startTimeRelay = BehaviorRelay.create<Long>()

    val recorderStateRelay = BehaviorRelay.createDefault<Boolean>(false)

    private val settingRelay = BehaviorRelay.create<RecorderSetting>()

    override fun buildRecorder(setting: RecorderSetting): Completable = Completable.fromAction {
        settingRelay.accept(setting)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(setting.audioSource)
            setOutputFormat(setting.outputFormat)
            setAudioEncoder(setting.audioEncoder)
            setOutputFile(setting.file.absolutePath)
            prepare()
        }
    }

    override fun startRecorder(): BehaviorRelay<Boolean> {
        startTimeRelay.accept(System.currentTimeMillis())
        recorderStateRelay.accept(true)
        mediaRecorder?.start()
        return recorderStateRelay
    }

    override fun stopRecorder() {
        mediaRecorder?.apply {
            stop()
            reset()
        }
        val stopTime = System.currentTimeMillis()
        if (stopTime - startTimeRelay.value / 1000 < 0) {
            val file = settingRelay.value.file
            if (file.exists())
                file.delete()
            insuffectTimeRelay.accept(true)
        } else {
            insuffectTimeRelay.accept(false)
        }
        recorderStateRelay.accept(false)
    }

    override fun clearRecorder(): Completable = Completable.fromAction {
        mediaRecorder?.apply {
            release()
        }
        recorderStateRelay.accept(false)
    }
}
