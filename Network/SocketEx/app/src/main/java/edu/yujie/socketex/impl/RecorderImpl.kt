package edu.yujie.socketex.impl

import android.media.MediaRecorder
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IRecorder
import edu.yujie.socketex.util.createFile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File

class RecorderImpl : IRecorder {
    private var mediaRecorder: MediaRecorder? = null

    private lateinit var setting: RecorderSetting

    private lateinit var file: File

    override val recordingStateRelay = BehaviorRelay.createDefault<Boolean>(false)//錄音狀態

    private var startTime: Long? = 0

    private val recordingTimeRelay = PublishRelay.create<Long>()

    private lateinit var recordingTime: Disposable

    override val enoughRecordingTimeRelay = BehaviorRelay.create<Boolean>()//錄音時間是否充足

    override fun prepareRecording(setting: RecorderSetting): Completable {
        this.setting = setting
        return Observable.just(setting.filePath)
            .map { it?.createFile(setting.fileName)!! }
            .map {
                file = it
                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(setting.audioSource)
                    setOutputFormat(setting.outputFormat)
                    setAudioEncoder(setting.audioEncoder)
                    setOutputFile(it.absolutePath)
                }
                mediaRecorder?.prepare()
            }.ignoreElements()
    }

    override fun startRecording(): PublishRelay<Long> {
        recordingStateRelay.accept(true)
        startTime = System.currentTimeMillis()
        mediaRecorder?.start()

        recordingTime = Observable.interval(setting.recordingTime, setting.recordingTimeUnit).subscribe {
            recordingTimeRelay.accept(it)
        }
        return recordingTimeRelay
    }

    override fun stopRecording(): Completable = Completable.fromAction {
        recordingTime.dispose()
        recordingStateRelay.accept(false)
        mediaRecorder?.apply {
            stop()
            release()
            mediaRecorder = null
        }

        startTime = if (startTime != null) startTime else System.currentTimeMillis()
        val stopTime = System.currentTimeMillis()
        val insteff: Long = (stopTime - startTime!!) / setting.millisecondInterval
        println("startTime:${startTime}, stopTime:${stopTime}, insteff:$insteff")
        if (insteff < 500) {
            if (file.exists()) file.delete()
            enoughRecordingTimeRelay.accept(false)
        } else {
            enoughRecordingTimeRelay.accept(true)
        }
    }

}
