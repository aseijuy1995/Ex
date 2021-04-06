package tw.north27.coachingapp.media

import android.media.MediaRecorder
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.io.FileNotFoundException
import java.util.concurrent.TimeUnit

class MediaRecorderModule : IMediaRecorderModule {

    private var mediaRecorder: MediaRecorder? = null

    override fun prepare(setting: RecorderSetting): Completable = Completable.fromAction {
        if (!setting.file.exists()) throw FileNotFoundException("Can't find audio file!")
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(setting.audioSource)
        mediaRecorder!!.setOutputFormat(setting.outputFormat)
        mediaRecorder!!.setAudioEncoder(setting.audioEncoder)
        mediaRecorder!!.setOutputFile(setting.file.absolutePath)
        mediaRecorder!!.prepare()
    }

    private var disposable: Disposable? = null

    override val timeRelay = PublishRelay.create<Long>()

    override fun start() {
        if (mediaRecorder == null) throw NullPointerException("MediaRecorder has been created!")
        mediaRecorder!!.start()
        val timeObs = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
        disposable = timeObs!!.subscribe {
            timeRelay.accept(it)
        }
    }

    override fun stop() {
        mediaRecorder!!.stop()
        mediaRecorder!!.reset()
        disposable?.dispose()
    }

    override fun release() {
        mediaRecorder?.release()
    }

//        val stopTime = System.currentTimeMillis()
//        val lengthTime: Int = ((stopTime - startTime!!) / 1000).toInt() / setting.shortLengthTime
//        if (lengthTime < 1) {
//            if (file!!.exists()) file!!.delete()
//            resultRelay.accept(Pair(false, null))
//        } else {
//            resultRelay.accept(Pair(true, RecorderResult(file = file, lengthTime = lengthTime)))
//        }
//        intervalDisposable?.dispose()
}
