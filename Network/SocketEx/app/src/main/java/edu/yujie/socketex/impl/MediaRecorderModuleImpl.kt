package edu.yujie.socketex.impl

import android.media.MediaRecorder
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.RecorderResult
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IMediaRecorderModule
import edu.yujie.socketex.util.createFile
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.File
import java.util.concurrent.TimeUnit

class MediaRecorderModuleImpl : IMediaRecorderModule {

    private var mediaRecorder: MediaRecorder? = null

    private lateinit var setting: RecorderSetting

    private var file: File? = null

//    override val stateRelay = BehaviorRelay.createDefault<Boolean>(false)
//
//    override val resultRelay = PublishRelay.create<Pair<Boolean, RecorderResult?>>()

    var lengthTime: Int = 0

    override val lengthTimeRelay = PublishRelay.create<Int>()

    private var startTime: Long? = 0

    private var intervalDisposable: Disposable? = null

    override fun buildRecorder(setting: RecorderSetting): MediaRecorder {
        this.setting = setting
        val path = setting.filePath
        file = path?.createFile(setting.fileName)!!

        val mediaRecorder = MediaRecorder().apply {
            setAudioSource(setting.audioSource)
            setOutputFormat(setting.outputFormat)
            setAudioEncoder(setting.audioEncoder)
            setOutputFile(file!!.absolutePath)
        }
        return mediaRecorder
    }

    override fun prepare(setting: RecorderSetting): Completable = Completable.fromAction {
        mediaRecorder = buildRecorder(setting).apply {
            prepare()
        }
    }

    override fun start() {
        mediaRecorder?.let {
            it.start()
            stateRelay.accept(true)

            startTime = System.currentTimeMillis()
            intervalDisposable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .map { it.toInt() }
                .subscribe {
                    lengthTime = it
                    lengthTimeRelay.accept(it)
                }
        }
    }

    override fun stop(): Completable = Completable.fromAction {
        mediaRecorder?.apply {
            stop()
            release()
            stateRelay.accept(false)

            val stopTime = System.currentTimeMillis()
            val lengthTime: Int = ((stopTime - startTime!!) / 1000).toInt() / setting.shortLengthTime
            if (lengthTime < 1) {
                if (file!!.exists()) file!!.delete()
                resultRelay.accept(Pair(false, null))
            } else {
                resultRelay.accept(Pair(true, RecorderResult(file = file, lengthTime = lengthTime)))
            }
            intervalDisposable?.dispose()
        }
    }
}
