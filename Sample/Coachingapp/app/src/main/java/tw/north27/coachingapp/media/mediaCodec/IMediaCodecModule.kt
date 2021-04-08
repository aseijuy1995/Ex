package tw.north27.coachingapp.media.mediaCodec

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import timber.log.Timber
import tw.north27.coachingapp.media.DecodeSetting
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

interface IMediaCodecModule {

    fun configDecoder(setting: DecodeSetting): IMediaCodecModule

//    suspend fun decode()
     fun decode()
}