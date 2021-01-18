package edu.yujie.socketex.impl

import android.content.Context
import android.media.MediaRecorder
import com.jakewharton.rxrelay3.BehaviorRelay
import edu.yujie.socketex.bean.RecorderSetting
import edu.yujie.socketex.inter.IRecorder
import edu.yujie.socketex.util.createFile
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

class RecorderImpl : IRecorder {
    private var mediaRecorder: MediaRecorder? = null

    private var startTime: Long? = null

    val recorderStateRelay = BehaviorRelay.createDefault<Boolean>(false)//錄音狀態

    private var observable: Observable<Long>? = null

    val insuffectTimeRelay = BehaviorRelay.createDefault<Boolean>(false)//時間不足

    val startTimeRelay = BehaviorRelay.createDefault<Long>(0)

    private var setting: RecorderSetting? = null

    private var file: File? = null

    override fun initRecorder(setting: RecorderSetting): Completable = Completable.fromAction {
        this.setting = setting
        Observable.just(setting.filePath)
            .map { it?.createFile(setting.fileName)!! }
            .map {
                file = it
                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(setting.audioSource)
                    setOutputFormat(setting.outputFormat)
                    setAudioEncoder(setting.audioEncoder)
                    setOutputFile(it.absolutePath)
                }
            }
    }

    override fun startRecorder(context: Context): Observable<Long> {
        recorderStateRelay.accept(true)
        startTime = System.currentTimeMillis()
        mediaRecorder?.apply {
            prepare()
            start()
        }
        return Observable.interval(setting!!.minimumInterval, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun stopRecorder(): BehaviorRelay<Boolean> {
        recorderStateRelay.accept(false)
        mediaRecorder?.apply {
            reset()
            stop()
            release()
        }
        val stopTime = System.currentTimeMillis()
        val minimumInterval = setting!!.minimumInterval

        if (stopTime - startTime!! / minimumInterval < 0) {
            if (file!!.exists())
                file!!.delete()
            insuffectTimeRelay.accept(true)
        } else {
            insuffectTimeRelay.accept(false)
        }
        return insuffectTimeRelay
    }

}
