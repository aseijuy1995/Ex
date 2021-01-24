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
import java.util.concurrent.TimeUnit

class RecorderImpl : IRecorder {
    private var mediaRecorder: MediaRecorder? = null

    private lateinit var setting: RecorderSetting

    private lateinit var file: File

    override val stateRelay = BehaviorRelay.createDefault<Boolean>(false)//錄音狀態

    override val lessTimeRelay = BehaviorRelay.createDefault<Boolean>(false)

    override val recordingTimeRelay = PublishRelay.create<Int>()

    private var startTime: Long? = 0

    private lateinit var disposable: Disposable

    override fun prepareRecording(setting: RecorderSetting): Completable = Completable.fromAction {
        this.setting = setting
        val path = setting.filePath
        file = path?.createFile(setting.fileName)!!
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(setting.audioSource)
            setOutputFormat(setting.outputFormat)
            setAudioEncoder(setting.audioEncoder)
            setOutputFile(file.absolutePath)
            prepare()
        }
    }

    override fun startRecording() {
        stateRelay.accept(true)
        startTime = System.currentTimeMillis()
        mediaRecorder?.start()

        disposable = Observable.interval(1, TimeUnit.SECONDS)
            .map { it.toInt() }
            .subscribe {
                recordingTimeRelay.accept(it)
            }
    }

    override fun stopRecording(): Completable = Completable.fromAction {
        stateRelay.accept(false)
        val stopTime = System.currentTimeMillis()
        val lengthTime: Int = ((stopTime - startTime!!) / 1000).toInt() / setting.shortLengthTimeSec
        if (lengthTime < 1) {
            if (file.exists()) file.delete()
            lessTimeRelay.accept(true)
        }
        disposable.dispose()

        mediaRecorder?.apply {
            stop()
            release()
//            mediaRecorder = null
        }
    }
}
