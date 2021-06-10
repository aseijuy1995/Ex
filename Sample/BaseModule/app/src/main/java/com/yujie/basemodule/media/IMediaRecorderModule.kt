package tw.north27.coachingapp.media

import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Completable

interface IMediaRecorderModule {

    val timeRelay: PublishRelay<Long>

    fun prepare(setting: RecorderSetting): Completable

    fun start()

    fun stop()

    fun release()

}